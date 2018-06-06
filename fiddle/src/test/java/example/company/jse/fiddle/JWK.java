package example.company.jse.fiddle;

public class JWK {

	String kty = "EC";
	String crv = "P-256";
	String x;
	String y;

	public String getKty() {
		return kty;
	}

	public void setKty(String kty) {
		this.kty = kty;
	}

	public String getCrv() {
		return crv;
	}

	public void setCrv(String crv) {
		this.crv = crv;
	}

	public String getX() {
		return x;
	}

	public void setX(String x) {
		this.x = x;
	}

	public String getY() {
		return y;
	}

	public void setY(String y) {
		this.y = y;
	}

}
