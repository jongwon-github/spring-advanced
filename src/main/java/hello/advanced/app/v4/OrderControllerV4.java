package hello.advanced.app.v4;

import hello.advanced.trace.logtrace.LogTrace;
import hello.advanced.trace.template.AbstractTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * AbstractTemplate<String>
 * 1. 제네릭을 String 으로 설정했다. 따라서 AbstractTemplate 의 반환 타입은 String 이 된다.
 * 2. 익명 내부 클래스
 *  - 익명 내부 클래스를 사용한다. 객체를 생성하면서 AbstractTemplate 를 상속받은 자식 클래스를 정의했다.
 *  - 따라서 별도의 자식 클래스를 직접 만들지 않아도 된다.
 * 3. template.execute("OrderController.request()")
 *  - 템플릿을 실행하면서 로그로 남길 message 를 전달한다.
 */
@RestController
@RequiredArgsConstructor
public class OrderControllerV4 {

    private final OrderServiceV4 orderService;
    private final LogTrace trace;

    @GetMapping("/v4/request")
    public String request(String itemId) {
        AbstractTemplate<String> template = new AbstractTemplate<>(trace) {
            @Override
            protected String call() {
                orderService.orderItem(itemId);
                return "ok";
            }
        };

        return template.execute("OrderController.request()");
    }

}
