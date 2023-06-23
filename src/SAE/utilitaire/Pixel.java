package SAE.utilitaire;

public class Pixel
{
    /**
     * Attributs
     */
    private int x;
    private int y;

    /**
     * Constructeur
     * @param x, x du pixel
     * @param y, y du pixel
     */
    public Pixel(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    /**
     * Getter et setter
     */

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
