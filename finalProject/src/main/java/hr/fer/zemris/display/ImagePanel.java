package hr.fer.zemris.display;

import hr.fer.zemris.tiles.Tile;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

@Deprecated
public class ImagePanel extends JComponent {

    private final int rowCount;
    private final int colCount;
    private final int frameNo;
    private BufferedImage img;
    private Tile[][] tiles;

    public ImagePanel(int rowCount, int colCount, hr.fer.zemris.Frame frame, int frameNo) {
        super();

        this.rowCount=rowCount;
        this.colCount=colCount;
        this.frameNo=frameNo;
        this.tiles = frame.getFrame();

        this.setSize(100*colCount, 100*rowCount);

        img = new BufferedImage(100*colCount, 100*rowCount, BufferedImage.TYPE_INT_RGB);
        Graphics imgG = img.getGraphics();

        int x=0, y=0;
        for (int k=0; k<rowCount; ++k){
            for (int i=0; i<colCount; ++i){
                imgG.drawImage(tiles[k][i].getImg(), x, y, this.getParent());
                x+=100;
            }
            y+=100;
            x=0;
        }

        imgG.dispose();
        //wait();
    }

    @Override
    public void paintComponent(Graphics g){

        //this.img=img;

        g.drawImage(img, 0, 0, this.getParent());
    }

    public BufferedImage getImg(){
        return img;
    }

}
