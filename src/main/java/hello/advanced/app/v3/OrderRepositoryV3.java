package hello.advanced.app.v3;

import hello.advanced.trace.TraceId;
import hello.advanced.trace.TraceStatus;
import hello.advanced.trace.logtrace.LogTrace;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryV3 {

    private final LogTrace trace;

    public void save(TraceId traceId, String itemId) {
        /*
            1. save() 는 파라미터로 전달 받은 traceId 를 사용해서 trace.beginSync() 를 실행한다.
            2. beginSync() 는 내부에서 다음 traceId 를 생성하면서 트랜잭션ID는 유지하고 level 은 하나 증가시킨다.
            3. beginSync() 는 이렇게 갱신된 traceId 로 새로운 TraceStatus 를 반환한다.
            4. trace.end(status) 를 호출하면서 반환된 TraceStatus 를 전달한다.
         */
        TraceStatus status = null;
        try {
            status = trace.begin("OrderRepository.save()");
            // 저장 로직
            if (itemId.equals("ex")) {
                throw new IllegalStateException("예외 발생!");
            }
            sleep(1000);
            trace.end(status);
        } catch(Exception e) {
            trace.exception(status, e);
            throw e; // 예외를 던짐
        }
    }

    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
