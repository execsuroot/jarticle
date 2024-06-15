package tech.execsuroot.jarticle.particle;

import de.exlll.configlib.Configuration;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Configuration
public class AnimationData {

    public static final int INFINITE_REPEAT = -1;

    private List<FrameData> frames;
    @Builder.Default
    private int repeat = INFINITE_REPEAT;
}
