package vo;

public class PageInfo {
	private int cpage, mpage, spage, epage, rcount;
	// ���� ��������ȣ, ������ ��������ȣ, ���� �� ���� ������ ��ȣ, ��ü �� ����
	private String schType, schType2, keyword, sdate, edate;
	// �˻�����, �˻�����2, �˻���, ���۳�¥, ���ᳯ¥
	
	public int getCpage() {
		return cpage;
	}
	public void setCpage(int cpage) {
		this.cpage = cpage;
	}
	public int getMpage() {
		return mpage;
	}
	public void setMpage(int mpage) {
		this.mpage = mpage;
	}
	public int getSpage() {
		return spage;
	}
	public void setSpage(int spage) {
		this.spage = spage;
	}
	public int getEpage() {
		return epage;
	}
	public void setEpage(int epage) {
		this.epage = epage;
	}
	public int getRcount() {
		return rcount;
	}
	public void setRcount(int rcount) {
		this.rcount = rcount;
	}
	public String getSchType() {
		return schType;
	}
	public void setSchType(String schType) {
		this.schType = schType;
	}
	public String getSchType2() {
		return schType2;
	}
	public void setSchType2(String schType2) {
		this.schType2 = schType2;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public String getSdate() {
		return sdate;
	}
	public void setSdate(String sdate) {
		this.sdate = sdate;
	}
	public String getEdate() {
		return edate;
	}
	public void setEdate(String edate) {
		this.edate = edate;
	}
	
}
