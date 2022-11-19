package hello.advanced.app.v2;

import hello.advanced.trace.TraceId;
import hello.advanced.trace.TraceStatus;
import hello.advanced.trace.hellotrace.HelloTraceV2;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceV2 {

    private final OrderRepositoryV2 orderRepository;
    private final HelloTraceV2 trace;

    public void orderItem(TraceId traceId, String itemId) {
        /*
            1. orderItem() 은 파라미터로 전달 받은 traceId 를 사용해서 trace.beginSync() 를 실행한다.
            2. beginSync() 는 내부에서 다음 traceId 를 생성하면서 트랜잭션ID는 유지하고 level 은 하나 증가시킨다.
            3. beginSync() 가 반환한 새로운 TraceStatus 를 orderRepository.save() 를 호출하면서 파라미터로 전달한다.
            4. TraceId 를 파라미터로 전달하기 위해 orderRepository.save() 의 파라미터에 TraceId 를 추가해야 한다.
         */
        TraceStatus status = null;
        try {
            status = trace.beginSync(traceId, "OrderService.orderItem()");
            orderRepository.save(status.getTraceId(), itemId);
            trace.end(status);
        } catch (Exception e) {
            trace.exception(status, e);
            throw e; // 예외를 던짐
        }
    }

}
