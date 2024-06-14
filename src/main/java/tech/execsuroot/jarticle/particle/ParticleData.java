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

    private ParticlePosition position = ParticlePosition.ZERO;
    private ParticlePosition offset = ParticlePosition.ZERO;
    private Particle type;
    private int amount = 1;
    private double speed = 0;
    private String data = null;
}
