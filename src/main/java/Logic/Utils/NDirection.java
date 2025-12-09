package Logic.Utils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public record NDirection(double nx, double ny) {
    public NDirection{
        double length = Math.hypot(nx,ny);
        try {
            nx /= length;
            ny /= length;
        } catch (Exception e) {
            log.debug("NDirection data is not valid!");
        }
    }
}
