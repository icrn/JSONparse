package cn.parse;

/**
 * Created by yan on 2016/10/22.
 */
public class JsonInfo {

    private String element = null;//ɨ�����������

    private String v = null;//�����map��ɨ�������value

    private boolean isMap = false;//����ᱻ�ж�ΪMAP

    private boolean isMark = false;// ��ʶ"

    private boolean isElementSeparate = false; //�ǲ��Ƕ���

    private int markIndex = 0;// ��ʶ�ж��ٸ�"

    public String getElement() {
        return element;
    }

    public void setElement(String element) {
        this.element = element;
    }

    public String getV() {
        return v;
    }

    public void setV(String v) {
        this.v = v;
    }

    public boolean isMap() {
        return isMap;
    }

    public void setIsMap(boolean isMap) {
        this.isMap = isMap;
    }

    public boolean isMark() {
        return isMark;
    }

    public void setIsMark(boolean isMark) {
        this.isMark = isMark;
    }

    public boolean isElementSeparate() {
        return isElementSeparate;
    }

    public void setIsElementSeparate(boolean isElementSeparate) {
        this.isElementSeparate = isElementSeparate;
    }

    public int getMarkIndex() {
        return markIndex;
    }

    public void setMarkIndex(int markIndex) {
        this.markIndex = markIndex;
    }
}
