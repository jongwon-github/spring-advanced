package hello.advanced.trace.template.code;

import lombok.extern.slf4j.Slf4j;

/**
 * 변하는 것과 변하지 않는 것을 분리
 * 1. 변하는 부분(핵심 기능) : 비즈니스 로직
 * 2. 변하지 않는 부분(부가 기능) : 시간 측정
 */
@Slf4j
public abstract class AbstractTemplate {

    public void execute() {
        long startTime = System.currentTimeMillis();
        // 비즈니스 로직 실행
        call();
        // 비즈니스 로직 종료
        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("resultTime={}", resultTime);
    }

    protected abstract void call();

}
