package src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Random;

class Cell {
    int x,y;
    int windowX, windowY;
    JButton digitBtn;
    int digit;

    public Cell(int x, int y, int windowX, int windowY, JButton digitBtn, int digit){
        this.x=x;
        this.y=y;
        this.windowX=windowX;
        this.windowY=windowY;
        this.digitBtn = digitBtn;
        this.digit=digit;
    }
    public String toString(){
        return String.valueOf(x)+", "+String.valueOf(y)+", "+String.valueOf(windowX)+", "+String.valueOf(windowY)+", "+digitBtn.getText();
    }
}

class MyWindow extends JFrame{
    static int HEIGHT = 820;
    static int WIDTH = 800;
    boolean startedGame = false;
    ArrayList<Cell> listCells = new ArrayList<>();

    public MyWindow(){
        setResizable(false);
        setSize(WIDTH, HEIGHT);
        setLocation(400, 20);
        Main mainPanel = new Main(this, WIDTH,HEIGHT);
        add(mainPanel);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        createCells();

        for(Cell cell : listCells){
            cell.digitBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            cell.digitBtn.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    if (SwingUtilities.isLeftMouseButton(e)) {
                        if(cell.digitBtn.isEnabled()){
                            if(cell.digit>9) cell.digit=1;
                            cell.digitBtn.setText(String.valueOf(cell.digit++));
                        }
                    }
                    if (SwingUtilities.isRightMouseButton(e)) {
                        if(cell.digitBtn.isEnabled()){
                            cell.digitBtn.setEnabled(false);
                        }else{
                            cell.digitBtn.setEnabled(true);
                        }
                    }
                    if (SwingUtilities.isMiddleMouseButton(e)) {
                        if(cell.digitBtn.isEnabled()){
                            cell.digit=1;
                            cell.digitBtn.setText("");
                        }
                    }
                }
            });
            add(cell.digitBtn);
        }
    }
    public void paint(Graphics g){
        g.setColor(new Color(50, 50, 50));
        g.fillRect(0,0,WIDTH,HEIGHT);
        if(startedGame){
            drawLines(g);
        }
    }

    private void createCells() {
        setLayout(null);
        int countHor = 0;
        int countVer = 0;
        for(int i=0; i<=WIDTH; i+= WIDTH/9){ // poziome
            for(int j=0; j<=HEIGHT; j+=(HEIGHT-20)/9){  // pionowe
                if(countHor <9 && countVer<9){
                    JButton digitBtn = new JButton("");
                    digitBtn.setFont(new Font("Arial", Font.BOLD, 32));
                    digitBtn.setBounds(i, j, (WIDTH/9)-10, ((HEIGHT-20)/9)-10);
                    digitBtn.setVisible(true);
                    digitBtn.setBackground(new Color(50, 50, 50));
                    digitBtn.setForeground(Color.white);
                    digitBtn.setBorder(null);
                    digitBtn.setMaximumSize(new Dimension(70,70));
                    Cell cell = new Cell(countVer,countHor, i, j, digitBtn, 1);
                    listCells.add(cell);
                }
                countHor++;
            }
            countVer++;
            countHor=0;
        }
    }

    private void drawLines(Graphics g) {
        int countHor = 0;
        int countVer = 0;
        g.setColor(Color.white);
        for(int i=WIDTH/9; i<=WIDTH-WIDTH/9; i+= WIDTH/9){ // poziome
            for(int j=(HEIGHT-20)/9; j<=HEIGHT-(HEIGHT-20)/9; j+=(HEIGHT-20)/9){  // pionowe
                if(countHor==2 || countHor==5 || countVer == 2 || countVer == 5){ // linie pomocnicze
                    g.setColor(Color.green);
                }else{
                    g.setColor(Color.white);
                }
                g.drawLine(j,0, j, HEIGHT);  // pionowe
                g.drawLine(0,i+20, WIDTH, i+20);   // poziome
                countHor++;
            }
            countVer++;
            countHor=0;
        }
    }

    public void setStartGame(Boolean startedGame){
        this.startedGame = startedGame;
    }

    public static void main(String[] args) {
        new MyWindow();
    }
}

class Main extends JPanel {
    public void setFontType(Label text, Integer size, Color color, Integer x, Integer y){
        text.setFont(new Font(null, 0, size));
        text.setVisible(true);
        text.setForeground(color);
        text.setLocation(x,y);
    }
    public Main(MyWindow window, Integer width, Integer height){
        setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        setBounds(0,0,width,height);
        setBackground(new Color(50, 50, 50));
        Label titleLabel = new Label("GAME SHOOTER");
        Label startLabel = new Label("Start Game");
        startLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                setVisible(false);
                window.setStartGame(true);
                window.repaint();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                startLabel.setForeground(Color.white);
            }
            @Override
            public void mouseExited(MouseEvent e){
                startLabel.setForeground(Color.red);
            }
        });
        setFontType(titleLabel, 72, Color.white, width/2, 10);
        setFontType(startLabel, 48, Color.red, width/2, 50);
        gc.gridx = 1;
        gc.gridy = 0;
        add(titleLabel,gc);
        gc.gridx = 1;
        gc.gridy = 3;
        gc.weighty = 200;
        add(startLabel,gc);

    }
}


