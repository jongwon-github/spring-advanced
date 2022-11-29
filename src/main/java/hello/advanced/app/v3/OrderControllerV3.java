package hello.advanced.app.v3;

import hello.advanced.trace.TraceStatus;
import hello.advanced.trace.logtrace.LogTrace;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderControllerV3 {

    private final OrderServiceV3 orderService;
    private final LogTrace trace;

    @GetMapping("/v3/request")
    public String request(String itemId) {
        /*
            1. TraceStatus status = trace.begin() 에서 반환 받은 TraceStatus 에는 트랜잭션ID 와 level 정보가 있는 TraceId 가 있다.
            2. orderService.orderItem() 을 호출할 때 TraceId 를 파라미터로 전달한다.
            3. TraceId 를 파라미터로 전달하기 위해 OrderServiceV2.orderItem() 의 파라미터에 TraceId 를 추가해야 한다.
        */
        TraceStatus status = null;
        try {
            status = trace.begin("OrderController.request()");
            orderService.orderItem(status.getTraceId(), itemId);
            trace.end(status);
            return "ok";
        } catch (Exception e) {
            trace.exception(status, e);
            throw e; // 예외를 던짐
        }
    }

}
