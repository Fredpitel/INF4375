package ca.uqam.projet.schema;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by deasel on 2016-05-16.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class BixiSchema {
    private int id;
    private String s;
    private String n;
    private int st;
    private boolean b;
    private boolean su;
    private boolean m;
    private long lu;
    private long lc;
    private String bk;
    private String bl;
    private float la;
    private float lo;
    private int da;
    private int dx;
    private int ba;
    private int bx;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }

    public String getN() {
        return n;
    }

    public void setN(String n) {
        this.n = n;
    }

    public int getSt() {
        return st;
    }

    public void setSt(int st) {
        this.st = st;
    }

    public boolean isB() {
        return b;
    }

    public void setB(boolean b) {
        this.b = b;
    }

    public boolean isSu() {
        return su;
    }

    public void setSu(boolean su) {
        this.su = su;
    }

    public boolean isM() {
        return m;
    }

    public void setM(boolean m) {
        this.m = m;
    }

    public long getLu() {
        return lu;
    }

    public void setLu(long lu) {
        this.lu = lu;
    }

    public long getLc() {
        return lc;
    }

    public void setLc(long lc) {
        this.lc = lc;
    }

    public String getBk() {
        return bk;
    }

    public void setBk(String bk) {
        this.bk = bk;
    }

    public String getBl() {
        return bl;
    }

    public void setBl(String bl) {
        this.bl = bl;
    }

    public float getLa() {
        return la;
    }

    public void setLa(float la) {
        this.la = la;
    }

    public float getLo() {
        return lo;
    }

    public void setLo(float lo) {
        this.lo = lo;
    }

    public int getDa() {
        return da;
    }

    public void setDa(int da) {
        this.da = da;
    }

    public int getDx() {
        return dx;
    }

    public void setDx(int dx) {
        this.dx = dx;
    }

    public int getBa() {
        return ba;
    }

    public void setBa(int ba) {
        this.ba = ba;
    }

    public int getBx() {
        return bx;
    }

    public void setBx(int bx) {
        this.bx = bx;
    }

    public BixiSchema(){

    }

    @Override
    public String toString() {
        return ""+s+" has "+ba+" available";
    }
}
