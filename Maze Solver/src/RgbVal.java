class RgbVal {
    int r;
    int g;
    int b;

    public RgbVal(int r, int g, int b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public boolean approx(RgbVal o, int tol) {
        int totoalDev = 0;
        if (Math.abs(r - o.r) > tol/2) {
            totoalDev += Math.abs(r - o.r);
            return false;
        }

        if (Math.abs(g - o.g) > tol/2) {
            totoalDev += Math.abs(g - o.g);
            return false;
        }

        if (Math.abs(b - o.b) > tol/2) {
            totoalDev += Math.abs(b - o.b);
            return false;
        }

        if(totoalDev > tol){
            return false;
        }

        return true;
    }
}