package hr.fer.zemris.util;

@Deprecated
public class Longlong {
    private long upper;
    private long lower;

    public Longlong(long upper, long lower){
        this.upper=upper;
        this.lower=lower;
    }

    public Longlong(){
        this(0, 0);
    }

    public Longlong rshift(int k){
        lower=lower>>k;
        if (upper%2==1) lower|= 0x8000000000000000L;
        upper=upper>>k;

        return this;
    }

    public Longlong lshift(int k){
        upper=upper<<k;
        if (lower<0) {
            upper|=1;
        }
        lower=lower<<k;

        return this;
    }

    @Override
    public String toString(){
        return String.format("%x %x", upper, lower);
    }

    public Longlong add(long num){
        long tmp=lower;
        lower+=num;
        if ((tmp^lower)<0) {
            upper+=1;
            lower=0;
        }

        return this;
    }

    public int bitCount(){
        return Long.bitCount(upper)+Long.bitCount(lower);
    }

    public long getUpper(){
        return upper;
    }

    public long getLower(){
        return lower;
    }

    public Longlong and(long upper1, long lower1){
        upper&=upper1;
        lower&=lower1;

        return this;
    }

    public Longlong and(Longlong num){
        return and(num.upper, num.lower);
    }

    public Longlong not(){
        upper=~upper;
        lower=~lower;

        return this;
    }
}
