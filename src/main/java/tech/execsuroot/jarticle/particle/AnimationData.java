package tech.execsuroot.jarticle.particle;

import de.exlll.configlib.Configuration;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Configuration
public class AnimationData {

    public static final int INFINITE_REPEAT = -1;

    private List<FrameData> frames;
    private int repeat = INFINITE_REPEAT;
}
