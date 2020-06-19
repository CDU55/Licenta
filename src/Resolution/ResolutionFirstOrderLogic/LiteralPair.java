package Resolution.ResolutionFirstOrderLogic;

public class LiteralPair {
	
	LiteralFOL literal1;
	LiteralFOL literal2;
	public LiteralPair(LiteralFOL literal1, LiteralFOL literal2) {
		super();
		this.literal1 = literal1;
		this.literal2 = literal2;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((literal1 == null) ? 0 : literal1.hashCode());
		result = prime * result + ((literal2 == null) ? 0 : literal2.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LiteralPair other = (LiteralPair) obj;
		if (literal1 == null) {
			if (other.literal1 != null)
				return false;
		} else if (!literal1.equals(other.literal1))
			return false;
		if (literal2 == null) {
			if (other.literal2 != null)
				return false;
		} else if (!literal2.equals(other.literal2))
			return false;
		return true;
	}
	
	

}
