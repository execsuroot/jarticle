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
    private boolean absolute = false;
    private int duration = INFINITE_DURATION;
    private int delay = 0;
}
