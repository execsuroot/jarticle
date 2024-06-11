package tech.execsuroot.jarticle.particle;

import de.exlll.configlib.Configuration;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Configuration
public class ParticlePosition {

    public static final ParticlePosition ZERO = new ParticlePosition(0, 0, 0);

    private double x;
    private double y;
    private double z;
}
