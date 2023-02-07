package hello.advanced.app.v5;

import hello.advanced.trace.logtrace.LogTrace;
import hello.advanced.trace.template.AbstractTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 *  1. AbstractTemplate<Void>
 *   - 제네릭에서 반환 타입이 필요한데, 반환할 내용이 없으면 Void 타입을 사용하고 null 을 반환하면 된다.
 *     참고로제네릭은기본타입인void,int 등을선언할수없다.
 */
@Service
@RequiredArgsConstructor
public class OrderServiceV5 {

    private final OrderRepositoryV5 orderRepository;
    private final LogTrace trace;

    public void orderItem(String itemId) {
        AbstractTemplate<Void> template = new AbstractTemplate<>(trace) {
            @Override
            protected Void call() {
                orderRepository.save(itemId);
                return null;
            }
        };
        template.execute("OrderService.orderItem()");
    }

}
