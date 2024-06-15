package tech.execsuroot.jarticle.particle;

import de.exlll.configlib.Configuration;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Configuration
public class FrameData {

    public static final int INFINITE_DURATION = -1;

    private List<ParticleData> particles;
    @Builder.Default
    private boolean absolute = false;
    @Builder.Default
    private int duration = INFINITE_DURATION;
    @Builder.Default
    private int delay = 0;
}
