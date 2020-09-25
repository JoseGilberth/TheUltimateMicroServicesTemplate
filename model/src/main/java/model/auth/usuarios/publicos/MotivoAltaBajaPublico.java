package model.auth.usuarios.publicos;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.CreatedDate;

import lombok.Data;

@Entity
@Table
@Data
public class MotivoAltaBajaPublico {

	@Id
	@Column(name = "id", updatable = false, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
  
	@Length( min = 10 , max = 500 , message = "{motivoAltaBaja.publico.motivo.length}" )
	@Column(length = 255, nullable = false, unique = false)
	private String motivo;

	@CreatedDate
	@Column(updatable = false)
	LocalDateTime fechaAlta;
 
	
}
