package JDBCproject.vo;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class EquiVO {
	private int equipment_id;
	private String equipmentstype_TYPE_ID;
	private String equipmentscompany_CO_ID;
	private String MODEL;
	private String SERIAL_NO;
	private Date PURCHASE_DATE;
	private int PRICE;
}
