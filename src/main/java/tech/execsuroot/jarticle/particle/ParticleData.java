package tech.execsuroot.jarticle.particle;

import de.exlll.configlib.Configuration;
import lombok.*;
import org.bukkit.Particle;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Configuration
public class ParticleData {

    @Builder.Default
    private ParticlePosition position = ParticlePosition.ZERO;
    @Builder.Default
    private ParticlePosition offset = ParticlePosition.ZERO;
    private Particle type;
    @Builder.Default
    private int amount = 1;
    @Builder.Default
    private double speed = 0;
    private String data;
}
