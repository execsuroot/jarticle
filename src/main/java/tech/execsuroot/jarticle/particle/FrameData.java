package tech.execsuroot.jarticle.particle;

import de.exlll.configlib.Configuration;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Configuration
public class FrameData {

    public static final int INFINITE_DURATION = -1;

    private List<ParticleData> particles;
    private int duration = INFINITE_DURATION;
    private int delay = 0;
}
