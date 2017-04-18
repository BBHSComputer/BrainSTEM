package bbhs.appbowl2017;

public class Pair{
    private int a;
    private int b;

    public Pair(int a, int b){
        this.a = a;
        this.b = b;
    }
    public boolean isSame(int a, int b){
        if((this.a == a && this.b == b)||(this.b == a && this.a == b)){
            return true;
        }
        return false;
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