package bbhs.appbowl2017;

public class Pair{
    private int a;
    private int b;

    public Pair(int a, int b){
        this.a = Math.min(a, b);
        this.b = Math.max(a, b);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + a;
        result = prime * result + b;
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        final Pair other = (Pair) obj;
        if (a != other.a) return false;
        if (b != other.b) return false;
        return true;
    }

    public boolean containsNum(int n){
        if(this.a == n || this.b == n){
            return true;
        }
        return false;
    }
    public int getOther(int n){
        if(this.a == n){
            return this.b;
        }else{
            return this.a;
        }
    }
}