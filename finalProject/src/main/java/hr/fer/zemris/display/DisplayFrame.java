package hr.fer.zemris.display;

import com.squareup.gifencoder.FloydSteinbergDitherer;
import com.squareup.gifencoder.GifEncoder;
import com.squareup.gifencoder.ImageOptions;
import hr.fer.zemris.Animation;
import hr.fer.zemris.tiles.Tile;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class DisplayFrame extends JFrame {
    JSpinner noOfFrames = new JSpinner();
    JSpinner noOfRows = new JSpinner();
    JSpinner noOfCols = new JSpinner();
    BufferedImage[] animationDisplay;

    public DisplayFrame(){
        super();

        setTitle("Animation generator");
        setLocation(400, 400);
        setSize(350, 250);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        initGui();
    }

    private void initGui(){
        getContentPane().setLayout(new GridLayout(3, 1));

        JPanel textPanel = new JPanel();
        textPanel.setLayout(new GridLayout(1,3));

        JLabel jFrameCount = new JLabel("frame count", SwingConstants.CENTER);
        JLabel jRowCount = new JLabel("row count", SwingConstants.CENTER);
        JLabel jColCount = new JLabel("column count", SwingConstants.CENTER);
        textPanel.add(jFrameCount);
        textPanel.add(jRowCount);
        textPanel.add(jColCount);

        getContentPane().add(textPanel);

        JPanel spinnerPannel = new JPanel();
        spinnerPannel.setLayout(new GridLayout(1, 3));
        spinnerPannel.add(noOfFrames);
        spinnerPannel.add(noOfRows);
        spinnerPannel.add(noOfCols);

        getContentPane().add(spinnerPannel);

        JButton generate = new JButton("Generate animation");
        getContentPane().add(generate);


        generate.addActionListener(e -> {
            try {
                int frameCount = (int) noOfFrames.getValue();
                int rowCount = (int) noOfRows.getValue();
                int colCount = (int) noOfCols.getValue();

                if (frameCount<=0 || rowCount<=0 || colCount<=0) throw new IllegalArgumentException();

                Animation animation=new Animation(frameCount, colCount, rowCount);
                int frameNo=1;
                while (!animation.done()){
                    animation.newFrame();
                    System.out.println("Done with frame "+frameNo++);
                }

                animationDisplay=new BufferedImage[frameCount];

                getContentPane().removeAll();
                setLayout(new GridLayout(1, 1));

                //BufferedImage[] images=new BufferedImage[frameCount];

                hr.fer.zemris.Frame[] frames = animation.getFrames();
                for (int k=0; k<frameCount; ++k) {
                    animationDisplay[k] = drawImage(rowCount, colCount, frames[k]);
                }

                saveGIF(frameCount, colCount, rowCount);


            }
            catch (RuntimeException ex){
                System.out.println("Doslo je do greske");
            }

            System.exit(0);
        });

    }

    /**
     *
     * @param colCount helps define the resolution of the image. Width equals 100*colCount
     * @param rowCount helps define the resolution of the image. Height equals 100*rowCount
     * @param frame object whose Tile field is being constructed into a single image
     * @return an image representation of the given frame
     */
    private BufferedImage drawImage(int rowCount, int colCount, hr.fer.zemris.Frame frame){
        BufferedImage img = new BufferedImage(100*colCount, 100*rowCount, BufferedImage.TYPE_INT_RGB);
        Graphics imgG = img.getGraphics();

        Tile[][] tiles = frame.getFrame();

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

        return img;
    }

    /**
     * Saves the defined field 'animationDisplay' to a .gif file
     * @param frameCount number of frames in the gif
     * @param colCount helps define the resolution of the image. Width equals 100*colCount
     * @param rowCount helps define the resolution of the image. Height equals 100*rowCount
     */
    private void saveGIF(int frameCount, int colCount, int rowCount){
        try (FileOutputStream outputStream = new FileOutputStream("animation.gif")) {
            ImageOptions options = new ImageOptions();

            //Set delay between each frame
            options.setDelay(1, TimeUnit.SECONDS);
            //Use Floyd Steinberg dithering as it yields the best quality
            options.setDitherer(FloydSteinbergDitherer.INSTANCE);

            GifEncoder encoder = new GifEncoder(outputStream, 100*colCount, 100*rowCount, 0);

            for (int k=0; k<frameCount; ++k){
                encoder.addImage(convertImageToArray(animationDisplay[k]), options);
            }
            encoder.finishEncoding();

        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Convert BufferedImage into RGB pixel array
     */
    public int[][] convertImageToArray(BufferedImage image) throws IOException {
        int[][] rgbArray = new int[image.getHeight()][image.getWidth()];
        for (int i = 0; i < image.getHeight(); i++) {
            for (int j = 0; j < image.getWidth(); j++) {
                rgbArray[i][j] = image.getRGB(j, i);
            }
        }
        return rgbArray;
    }

    public static void main(String[] args){
        new DisplayFrame().setVisible(true);
    }
}
