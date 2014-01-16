import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;

/*  Private Variables:
 * 
 *  JPanel 
 *  startPage: The beginning page 
 *  map: the map the game primarily takes place in (the hallway)
 *  battlefield: the map where students are tutored (real-time battle)
 *  instruct: instructions are listed here in a JTextArea, returns to startPage
 *  reference: same as instruct, except returns to map. 
 *  teacher: this is where the exam takes place (turn-based battle)
 *  gameOver: where you go when you die. 
 *  status: your current progress can be viewed at any time. 
 *  
 *  CardLayout 
 *  cards: this is how the panels are organized, so they can be switched.
 *  
 *  Container 
 *  c: this is where all the panels are.
 *  
 *  String 
 *  mathTest: type of test taken
 *  calculator: current calculator 
 *  calculatorAbility: calculator's ability
 *  calculatorAbilityDescription: description of calculator's ability.
 *  headphones: current headphones
 *  upgradeTextH: text for the JMenuItem upgradeHeadphones 
 *  upgradeTextC: text fot the JMenuItem upgradeCalc 
 *  
 *  int
 *  mapMainCharX: Sephiroth's x coordinates
 *  mapMainCharY: Sephiroth's y coordinates
 *  level: amount of people defeated 
 *  hp: current health
 *  gunMag: ammo currently in Integral
 *  magCap: max ammo that can be loaded at once 
 *  totalAmmo: extra ammo
 *  maxAmmo: maximum extra ammo
 *  healCount: times healed
 *  iteration: stage of the game.
 *  enemyIndex: used to determine which enemy is defeated in MapPanel
 *  score: your cash.
 *  maxHP: maximum health
 *  upgradeCalcCost: cost to upgrade calculator
 *  upgradeHeadphoneCost: cost to upgrade headphones
 *  calcPower: highest accessible headphone
 *  atkBoost: the boost provided to attack
 *  pfd: amount of Partial Fraction Decompositions
 *  apocalypse: amount of Instant A's
 *  
 *  double
 *  HealPower:  healing multiplier
 *  
 *  ArrayList<Integer>
 *  enemyX: x coordinates of enemies. Can remove dead enemies.
 *  enemyY: y coordinates of enemies. Can remove dead enemies.
 *  
 *  JMenuBar:
 *  fullMenu: the bar that holds all the menus
 *  
 *  FileOptions: a JMenu
 *  JMenuItems contained:
 *  quit: Return to Start Panel
 *  escape: Return to Map
 *  stat: View Current Status
 *  information: View Instructions
 *  
 *  Store: a JMenu
 *  JMenuItems contained: 
 *  upgradeHeal: upgrade the headphones
 *  upgradeCalc: upgrade the calculator
 *  buyAmmo: buy ammo
 *  buyPFD: buy PFD
 *  buyApocalypse: buy apocalypse 
 *  fullHeal: heal all HP
 *  
 *  CalculatorMenu: a JMenu
 *  switch between all the calculators. all listed as radioButtons
 *  
 *  Retake Test: move to a previous level, or select any level you want. 
 *  switch between listed tests as radioButtons
 *  
 */

@SuppressWarnings("serial")
public class MathematicaMaelstrom extends JApplet implements ActionListener 
{  
    // private variable declarations
    private JPanel startPage, map, battlefield, instruct, reference, teacher, gameOver, status;
    private CardLayout cards; 
    private Container c;  
    private String mathTest, calculator, calculatorAbility, calculatorAbilityDescription, headphones, upgradeTextH, upgradeTextC; 
    private int mapMainCharX, mapMainCharY, level, hp, gunMag, magCap, totalAmmo, maxAmmo, healCount, iteration, enemyIndex, score;
    private int maxHP, upgradeCalcCost, upgradeHeadphoneCost, calcPower, atkBoost, pfd, apocalypse; 
    private double healPower; 
    private ArrayList<Integer> enemyX;
    private ArrayList<Integer> enemyY;
    private JMenuBar fullMenu;
    private JMenu FileOptions, Store, CalculatorMenu, RetakeExam;
    private JMenuItem quit, escape, stat, information;
    private JMenuItem upgradeHeal, upgradeCalc, buyAmmo, fullHeal, buyPFD, buyApocalypse;
    private JRadioButtonMenuItem ti30, ti84, ti89, tiInspire, wa; 
    private ButtonGroup testButtons, calcButtons;
    private JMenuItem satI, satII1, satII2, act, calcAB, calcBC, calcD;

    public void init()
    {
        // Levels
        enemyIndex = 0; 
        level = 0; 
        iteration = 0;
        score = 0; 
        
        // calculator stuff
        calculator = "None";
        calculatorAbility = "None"; 
        calculatorAbilityDescription = "None";
        calcPower = 1; 
        upgradeCalcCost = 10000;
        atkBoost = 0; 
        upgradeTextC = "Upgrade Calculator for $" + upgradeCalcCost; 
        
        // Integral
        gunMag = 6;
        magCap = 6;
        totalAmmo = 60;
        maxAmmo = 60; 
        
        // healing + cash
        hp = 500;
        maxHP = 500;
        headphones = "FMJ by SkullCandy";
        healCount = 0; 
        healPower = 1; 
        upgradeTextH = "Upgrade Headphones for $" + upgradeHeadphoneCost;
        upgradeHeadphoneCost = 5000;    
        
        // special items
        pfd = 0;
        apocalypse = 0; 
        
        //Menu
        fullMenu = new JMenuBar();

        // Game Options
        FileOptions = new JMenu("Transcript"); 

        stat = new JMenuItem("Current Progress Report");
        stat.addActionListener(this);
        FileOptions.add(stat); 

        quit = new JMenuItem("Quit Game"); 
        quit.addActionListener(this);
        FileOptions.add(quit); 

        escape = new JMenuItem("Ditch Study Session"); 
        escape.addActionListener(this);
        escape.setEnabled(false);
        FileOptions.add(escape);    
        
        information = new JMenuItem("Instructions");
        information.addActionListener(this);
        FileOptions.add(information); 

        // Store Menu
        Store = new JMenu("Student Store"); 
        
        upgradeCalc = new JMenuItem("Upgrade Calculator for $10000");
        upgradeCalc.addActionListener(this);
        Store.add(upgradeCalc); 

        upgradeHeal = new JMenuItem("Upgrade Headphones for $5000");
        upgradeHeal.addActionListener(this);
        Store.add(upgradeHeal);

        buyAmmo = new JMenuItem("Buy Limits of Integration for $1000");
        buyAmmo.addActionListener(this);
        Store.add(buyAmmo); 

        buyPFD = new JMenuItem("Recharge P.F.D.  for $10000");
        buyPFD.addActionListener(this);
        buyPFD.setEnabled(false);
        Store.add(buyPFD);

        buyApocalypse = new JMenuItem("Recharge Instant A+ for $25000");
        buyApocalypse.addActionListener(this);
        buyApocalypse.setEnabled(false);
        Store.add(buyApocalypse); 

        fullHeal = new JMenuItem("Buy Tylenol for $2000");
        fullHeal.addActionListener(this);
        Store.add(fullHeal); 

        // Calculator Menu
        CalculatorMenu = new JMenu("Calculator"); 
        calcButtons = new ButtonGroup(); 

        ti30 = new JRadioButtonMenuItem("TI30X-IIA");
        ti30.addActionListener(this);
        ti30.setEnabled(true); 
        ti30.setSelected(true);
        calcButtons.add(ti30);
        CalculatorMenu.add(ti30); 

        ti84 = new JRadioButtonMenuItem("TI84-PLUS");
        ti84.addActionListener(this);
        ti84.setEnabled(false);
        calcButtons.add(ti84);
        CalculatorMenu.add(ti84); 

        ti89 = new JRadioButtonMenuItem("TI89-TITANIUM");
        ti89.addActionListener(this);
        ti89.setEnabled(false);
        calcButtons.add(ti89);
        CalculatorMenu.add(ti89); 

        tiInspire = new JRadioButtonMenuItem("TI-Inspire");
        tiInspire.addActionListener(this);
        tiInspire.setEnabled(false);
        calcButtons.add(tiInspire);
        CalculatorMenu.add(tiInspire); 

        wa = new JRadioButtonMenuItem("WolframAlpha");
        wa.addActionListener(this);
        wa.setEnabled(false);
        calcButtons.add(wa); 
        CalculatorMenu.add(wa); 

        //Retake Exams
        RetakeExam = new JMenu("Retake Exam");
        testButtons = new ButtonGroup(); 

        satI = new JRadioButtonMenuItem("SAT I Math");
        satI.addActionListener(this);
        satI.setEnabled(true);
        satI.setSelected(true);
        testButtons.add(satI);
        RetakeExam.add(satI);

        satII1 = new JRadioButtonMenuItem("SAT II Math Level 1");
        satII1.addActionListener(this);
        satII1.setEnabled(false);
        testButtons.add(satII1);
        RetakeExam.add(satII1);

        satII2 = new JRadioButtonMenuItem("SAT II Math Level 2");
        satII2.addActionListener(this);
        satII2.setEnabled(false);
        testButtons.add(satII2);
        RetakeExam.add(satII2);

        act = new JRadioButtonMenuItem("American College Test (ACT)");
        act.addActionListener(this);
        act.setEnabled(false);
        testButtons.add(act);
        RetakeExam.add(act);

        calcAB = new JRadioButtonMenuItem("AP Calculus AB");
        calcAB.addActionListener(this);
        calcAB.setEnabled(false);
        testButtons.add(calcAB);
        RetakeExam.add(calcAB);

        calcBC = new JRadioButtonMenuItem("AP Calculus BC");
        calcBC.addActionListener(this);
        calcBC.setEnabled(false);
        testButtons.add(calcBC);
        RetakeExam.add(calcBC);

        calcD = new JRadioButtonMenuItem("Multivariable Calculus (Calc D)");
        calcD.addActionListener(this);
        calcD.setEnabled(false);
        testButtons.add(calcD);
        RetakeExam.add(calcD);
        
        configureTest(); 
        
        // Adding the menus to the menubar
        fullMenu.add(FileOptions);
        fullMenu.add(Store);
        fullMenu.add(CalculatorMenu); 
        fullMenu.add(RetakeExam); 
        setJMenuBar(fullMenu); 
        fullMenu.setVisible(false); 

        //Enemy Locations
        initializeEnemies(); 
        
        // Initialize Panels
        c = this.getContentPane(); 
        cards = new CardLayout();
        c.setLayout(cards); 

        startPage= new StartPanel(); 
        startPage.setBackground(Color.magenta); 
        c.add(startPage, "Start"); 

        map = new MapPanel(); 
        c.add(map, "Map"); 

        battlefield = new BattlePanel(); 
        c.add(battlefield, "Battle"); 

        instruct = new InstructionPanel(false);
        c.add(instruct, "Instructions"); 

        reference = new InstructionPanel(true);
        c.add(reference, "Reference"); 

        teacher = new ExaminationPanel(); 
        c.add(teacher, "Exam"); 

        gameOver = new GameOverPanel(); 
        c.add(gameOver, "Game Over"); 
        
        status = new StatusPanel();
        c.add(status, "Status Report"); 
    }
    
    public void initializeEnemies() // 6 enemies, 6 xy coordinates, initalize main character's position
    {
        enemyX = new ArrayList<Integer>();  
        enemyX.add(100);
        enemyX.add(400);
        enemyX.add(700); 
        enemyX.add(100);
        enemyX.add(400);
        enemyX.add(700); 

        enemyY = new ArrayList<Integer>();  
        enemyY.add(100);
        enemyY.add(100);
        enemyY.add(100); 
        enemyY.add(400);
        enemyY.add(400);
        enemyY.add(400);
        
        mapMainCharX = 500;
        mapMainCharY = 300; 
    }
    public void initializeMaximum() // set maximum for magazine capacity, ammo capacity and HP
    {
        magCap = 6 + iteration; // lower ammo if moving to lower setting
        if(totalAmmo > maxAmmo) 
            totalAmmo = maxAmmo; 
        maxAmmo = 60 + iteration*10; // lower ammo if moving to lower setting
        if (gunMag > magCap)
            gunMag = magCap; 
        maxHP = (int)(500 + iteration*25 + 25*((healPower-1)*2)); // lower hp if moving to lower settng
        if (hp >= maxHP)
            hp = maxHP;
    }
    
    /* START MENU */
    class StartPanel extends JPanel implements ActionListener
    {
        JButton kaishi, instructions; // buttons
        Image background; // bg
        String startLevel; // start button text

        public StartPanel()
        {
            this.setLayout(null); // nulllayout
            
            // Start Button
            startLevel = "Start New Game";
            kaishi = new JButton(startLevel);
            kaishi.addActionListener(this);
            kaishi.setBounds(400, 200, 150, 40); 
            this.add(kaishi);
            
            // Instructions
            instructions = new JButton("Instructions");
            instructions.addActionListener(this);
            instructions.setBounds(400, 400, 150, 40); 
            this.add(instructions);
            
            background = getImage(getDocumentBase(), "Math_BG.jpg");
            WaitForImage (this, background, 1);
        }

        public void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            g.drawImage(background, 0, 0, this); // bg
            g.setFont(new Font("Helvetica", Font.BOLD, 40));
            g.setColor(Color.yellow); 
            g.drawString("Mathematica Maelstrom", 300, 50); // title
        }

        public void actionPerformed(ActionEvent evt)
        {
            String command = evt.getActionCommand();

            if (command.equals(startLevel)) // switch to map
                cards.show(c, "Reference"); 
            if (command.equals("Instructions")) // switch to instruction
                cards.show(c, "Instructions");  
        }
        
        public void updateIteration() // set text for Start Button callable by other classes
        {
            startLevel = "Start " + mathTest + " test";
            kaishi.setText(startLevel);
        }

    }

    class MapPanel extends JPanel
    { 
        // instance declarations
        GraphicsPanel mapArea; 
        StatsPanel mapStats; 
        boolean gun, left; // if wielding the Integral, or facing left
        int combo; // changes 

        Image charImage, scientist, satIIboss; // currently selected image, two bosses
        Image leftStand, rightStand, leftWalk, rightWalk; // walking animations
        Image leftStandGun, rightStandGun, leftWalkGun, rightWalkGun, leftHeal, rightHeal; // action animations
        Image hallway; // bg 

        public MapPanel()
        {
            // Stand/Walk for The Derivative
            leftStand = getImage(getDocumentBase(), "sephiroth_left_standing.gif");
            WaitForImage (this, leftStand, 1);
            rightStand = getImage(getDocumentBase(), "sephiroth_right_standing.gif");
            WaitForImage (this, rightStand, 2);
            leftWalk = getImage(getDocumentBase(), "sephiroth_left_walking.gif");
            WaitForImage (this, leftWalk, 3);
            rightWalk = getImage(getDocumentBase(), "sephiroth_right_walking.gif");
            WaitForImage (this, rightWalk, 4);
            charImage = leftStand; 

            // Stand/Walk for The Integral
            leftStandGun = getImage(getDocumentBase(), "sephiroth_left_standing_gun.gif");
            WaitForImage (this, leftStandGun, 5);
            rightStandGun = getImage(getDocumentBase(), "sephiroth_right_standing_gun.gif");
            WaitForImage (this, rightStandGun, 6);
            leftWalkGun = getImage(getDocumentBase(), "sephiroth_left_walking_gun.gif");
            WaitForImage (this, leftWalkGun, 7);
            rightWalkGun = getImage(getDocumentBase(), "sephiroth_right_walking_gun.gif");
            WaitForImage (this, rightWalkGun, 8); 
            leftHeal = getImage(getDocumentBase(), "sephiroth_left_healing.gif");
            WaitForImage (this, leftHeal, 9);
            rightHeal = getImage(getDocumentBase(), "sephiroth_right_healing.gif");
            WaitForImage (this, rightHeal, 10); 

            // Enemies
            scientist = getImage(getDocumentBase(), "scientist.jpg");
            WaitForImage (this, scientist, 11);
            satIIboss = getImage(getDocumentBase(), "satII.jpg");
            WaitForImage (this, satIIboss, 12);

            //Background
            hallway = getImage(getDocumentBase(), "high_school_hallway.jpg");
            WaitForImage(this, hallway, 13);

            gun = false;
            left = true;  
            combo = 0; 

            // Initialization
            charImage = leftStand;

            //Adding components
            this.setLayout(new BorderLayout());
            this.add(fullMenu, BorderLayout.NORTH);
            mapArea = new GraphicsPanel(); 
            this.add(mapArea, BorderLayout.CENTER); 
            mapStats = new StatsPanel();
            this.add(mapStats, BorderLayout.SOUTH);

            //Character setting
            mapMainCharX = 500;
            mapMainCharY = 300;
        }

        class GraphicsPanel extends JPanel implements KeyListener
        {
            GraphicsPanel()
            {
                addKeyListener(this); // keylistener registration
            }

            public void paintComponent(Graphics g) 
            {
                super.paintComponent(g);
                setJMenuBar(fullMenu);
                this.requestFocus();
                //draw Character 
                g.drawImage(hallway, 0, 0, 1000, 600, this);
                drawCharacter(g);   
                drawEnemy(g);
                // draw boss
                g.drawImage(satIIboss, 850, 250, this); 
                // draw HUD
                drawGunMag(g);
                drawHP(g);
            }

            public void drawCharacter(Graphics g) // draw main character
            {
                g.drawImage(charImage, mapMainCharX, mapMainCharY, this);
            }

            public void drawEnemy(Graphics g) // draw enemies
            {
                for(int i = 0; i < enemyX.size(); i++)
                    g.drawImage(scientist ,enemyX.get(i), enemyY.get(i), this);
            }

            public void drawHP(Graphics g)// draw HP bar
            {
                g.setColor(Color.white);
                g.setFont(new Font("Helvetica", Font.BOLD, 20));
                g.drawString("MH", 10, 30);
                g.setColor(Color.red);
                g.fillRect (50, 10, maxHP, 20);
                g.setColor(Color.green); 
                g.fillRect (50, 10, hp, 20);    
            }

            public void drawGunMag(Graphics g) // draw Gun Mag
            {
                g.setFont(new Font("Helvetica", Font.BOLD, 36));
                g.setColor(Color.white);
                g.drawString("" + gunMag, 850, 50);
                g.setFont(new Font("Helvetica", Font.BOLD, 36));
                g.setColor(Color.white);
                g.drawString("| " + totalAmmo, 900, 50);
            }

            public void keyTyped (KeyEvent e) {} 

            public void keyPressed (KeyEvent e) 
            {
                int keyval = e.getKeyCode(); // get keyboard input 

                switch (keyval)
                {
                case KeyEvent.VK_A: // pressed left 
                    mapMainCharX -= 5 ; // go left
                    left = true; // facing left
                    if(gun)
                        charImage = leftWalkGun; 
                    else 
                        charImage = leftWalk;
                    break;
                case KeyEvent.VK_D: // pressed right
                    mapMainCharX += 5 ;// go right
                    left = false; // not facing left
                    if(gun)
                        charImage = rightWalkGun; 
                    else 
                        charImage = rightWalk;
                    break; 
                case KeyEvent.VK_W: // pressed up
                    mapMainCharY -= 5 ;// go up
                    break;
                case KeyEvent.VK_S: // pressed down
                    mapMainCharY += 5 ;// go down
                    break; 
                case KeyEvent.VK_R: // reload the Integral
                    int bullets =  magCap - gunMag;
                    
                    if (left)
                        charImage = leftHeal;
                    else if (!left)
                        charImage = rightHeal; 
                    
                    if (gunMag + totalAmmo < magCap) // if less than magCap, all ammo goes into Integral.
                    {
                        gunMag += totalAmmo;
                        totalAmmo = 0; 
                    }
                    else if (totalAmmo > 0) // if ammo available, reload as normal
                    {
                        gunMag = magCap;
                        totalAmmo -= bullets; 
                    }

                    if (totalAmmo <= 0) // can't have neg bullets
                        totalAmmo = 0; 
                    break; 
                case KeyEvent.VK_H: // HEALING
                    if (left)
                        charImage = leftHeal;
                    else if (!left)
                        charImage = rightHeal; 
                    hp += (int)((50 + 15*iteration)*healPower);  // heal 
                    healCount++; 
                    if (hp >= maxHP) // don't exceed max HP
                        hp = maxHP; 
                    if (healCount%10 == 0) // deal 250 dmg every 10 heals
                    {
                        hp -= 250;
                        score -= (100+ 5*iteration); 
                        testScore();
                    }   
                }

                outOfBounds(); // keep character inbounds
                encounter(); // deal with encounters
                repaint(); // update panel
            }           

            public void keyReleased (KeyEvent e) 
            {
                // return to standing resting animations
                if (charImage == leftWalk)
                    charImage = leftStand; 
                else if (charImage == rightWalk)
                    charImage = rightStand; 
                else if (charImage == leftWalkGun)
                    charImage = leftStandGun; 
                else if (charImage == rightWalkGun)
                    charImage = rightStandGun; 
                else if(charImage == leftHeal)
                {
                    if (gun)
                        charImage = leftStandGun;
                    else
                        charImage = leftStand;
                }
                else if(charImage == leftHeal)
                {
                    if (gun)
                        charImage = rightStandGun;
                    else
                        charImage = rightStand;
                }
                
                encounter();
                repaint(); 
            }

            public void encounter()
            {               
                // update text for both panels
                updateText(); 
                ((BattlePanel)battlefield).updateText(); 
                
                if(hp <= 0) // no hp = game over
                {
                    hp = maxHP; 
                    cards.show(c, "Game Over");
                }
                
                // take center of charcter
                int xCore = mapMainCharX + 26;
                int yCore = mapMainCharY + 36;

                for(int i = 0; i < enemyX.size(); i++) // if hit enemy go to battle
                {
                    if ((xCore >= enemyX.get(i) && xCore <= enemyX.get(i) + 55) 
                            && (yCore >= enemyY.get(i) && yCore <= enemyY.get(i)+77))
                    {
                        enemyIndex = i; 
                        if (left)
                            charImage = leftStand; 
                        else 
                            charImage = rightStand;
                        prepareForBattle();
                        cards.show(c, "Battle");
                        break; 
                    }
                }
                
                // after defeating all enemies, go to battle w/ examination
                if (xCore >= 850 && xCore <= 929 && yCore >= 250 && yCore <= 320 && level == 6) 
                {
                    prepareForBattle(); // set the JMenubar correctly
                    if (left)
                        charImage = leftStand; 
                    else 
                        charImage = rightStand;
                    
                    // initalize examination based on calculator used
                    if(wa.isSelected())
                    {
                        ((ExaminationPanel)teacher).enableIA();
                        ((ExaminationPanel)teacher).disablePFD();
                    }
                    else if (tiInspire.isSelected())
                    {
                        ((ExaminationPanel)teacher).enablePFD();
                        ((ExaminationPanel)teacher).disableIA();
                    }
                    ((ExaminationPanel)teacher).updateText();
                    cards.show(c, "Exam");
                }
            }

            public void outOfBounds()
            {
                if (mapMainCharX <= 5) // leftbounds = 5
                    mapMainCharX = 5;
                else if (mapMainCharX >= 960) // rightbounds = 960
                    mapMainCharX = 960;     
                if (mapMainCharY <= 5) // upbounds = 5
                    mapMainCharY = 5;
                else if (mapMainCharY >= 410) // downbounds = 410
                    mapMainCharY = 410; 
            }   
        }   
        
        public void updateText() // update text for this panel
        {
            mapStats.setText(); 
        }
    }   

    class BattlePanel extends JPanel
    {   
        int battleCharX, battleCharY;
        Image battleCharImage; 
        boolean gun, left; // if wielding the Integral, or facing left
        int combo; // determines type of combo
        int eX; // enemy x coor
        int eY; // enemy y coor
        int enemyHP; 
        CombatPanel combat; 
        StatsPanel BattleStats; // keeps track of stats
        Image charImage, scientist, classroom; // 
        Image leftStand, rightStand, leftWalk, rightWalk; // stand/walk animation
        Image leftStandGun, rightStandGun, leftWalkGun, rightWalkGun; // integral related animation
        Image leftAtk, rightAtk, leftShoot, rightShoot, leftHeal, rightHeal; // action animation
        Image leftAtk2, rightAtk2, leftAtk3, rightAtk3, leftAtk4, rightAtk4;  // derivative animations

        public BattlePanel()
        {
            this.setBackground(Color.RED); 
            this.setLayout(new BorderLayout()); 

            // Stand/Walk for The Derivative
            leftStand = getImage(getDocumentBase(), "sephiroth_left_standing.gif");
            WaitForImage (this, leftStand, 1);
            rightStand = getImage(getDocumentBase(), "sephiroth_right_standing.gif");
            WaitForImage (this, rightStand, 2);
            leftWalk = getImage(getDocumentBase(), "sephiroth_left_walking.gif");
            WaitForImage (this, leftWalk, 3);
            rightWalk = getImage(getDocumentBase(), "sephiroth_right_walking.gif");
            WaitForImage (this, rightWalk, 4);
            charImage = leftStand; 

            // Stand/Walk for The Integral
            leftStandGun = getImage(getDocumentBase(), "sephiroth_left_standing_gun.gif");
            WaitForImage (this, leftStandGun, 5);
            rightStandGun = getImage(getDocumentBase(), "sephiroth_right_standing_gun.gif");
            WaitForImage (this, rightStandGun, 6);
            leftWalkGun = getImage(getDocumentBase(), "sephiroth_left_walking_gun.gif");
            WaitForImage (this, leftWalkGun, 7);
            rightWalkGun = getImage(getDocumentBase(), "sephiroth_right_walking_gun.gif");
            WaitForImage (this, rightWalkGun, 8); 

            // Math Images
            leftAtk = getImage(getDocumentBase(), "sephiroth_left_atk1.gif");
            WaitForImage (this, leftAtk, 9);
            rightAtk= getImage(getDocumentBase(), "sephiroth_right_atk1.gif");
            WaitForImage (this, rightAtk, 10);
            leftShoot = getImage(getDocumentBase(), "sephiroth_left_shoot.gif");
            WaitForImage (this, leftShoot, 11);
            rightShoot = getImage(getDocumentBase(), "sephiroth_right_shoot.gif");
            WaitForImage (this, rightShoot, 12);
            leftHeal = getImage(getDocumentBase(), "sephiroth_left_healing.gif");
            WaitForImage (this, leftHeal, 13);
            rightHeal = getImage(getDocumentBase(), "sephiroth_right_healing.gif");
            WaitForImage (this, rightHeal, 14); 

            charImage = leftStand; 

            // Combo Images
            leftAtk2 = getImage(getDocumentBase(), "sephiroth_left_atk2.gif");
            WaitForImage (this, leftAtk2, 15);
            rightAtk2= getImage(getDocumentBase(), "sephiroth_right_atk2.gif");
            WaitForImage (this, rightAtk2, 16);
            leftAtk3 = getImage(getDocumentBase(), "sephiroth_left_atk3.gif");
            WaitForImage (this, leftAtk3, 17);
            rightAtk3= getImage(getDocumentBase(), "sephiroth_right_atk3.gif");
            WaitForImage (this, rightAtk3, 18);
            leftAtk4 = getImage(getDocumentBase(), "sephiroth_left_atk4.gif");
            WaitForImage (this, leftAtk4, 19);
            rightAtk4 = getImage(getDocumentBase(), "sephiroth_right_atk4.gif");
            WaitForImage (this, rightAtk4, 20);
            scientist = getImage(getDocumentBase(), "scientist.jpg");
            WaitForImage (this, scientist, 21);

            //Background Setting
            classroom = getImage(getDocumentBase(), "classroom.jpg");
            WaitForImage (this, classroom, 23);

            combat = new CombatPanel(); 
            this.add(combat, BorderLayout.CENTER);
            BattleStats = new StatsPanel();
            this.add (BattleStats, BorderLayout.SOUTH);

            initEnemy();
        }

        public void updateText() 
        {
            BattleStats.setText();
        }

        public void initEnemy()
        {
            eX = 300;
            eY = 300;
            enemyHP = 500 + level*75; 
            battleCharImage = leftStand; 
            battleCharX = 800; 
            battleCharY = 300;  
            left = true; 
        }

        class CombatPanel extends JPanel implements KeyListener, MouseListener, FocusListener, ActionListener
        {
            Timer enemyMove; // timer
            int randomMovement; // direction of enemy
            
            CombatPanel()
            {
                addKeyListener(this);
                addMouseListener(this); 
                addFocusListener(this); 
                this.setBackground(Color.green);
                enemyMove = new Timer(25, this); // change position every 25 milliseonds
                randomMovement = (int)(Math.random()*8); // 8 directions
            }

            public void paintComponent(Graphics g) 
            {
                super.paintComponent(g);
                this.requestFocus();
                //draw Character 
                g.drawImage(classroom, 0, 0, 1000, 600, this); // bg
                drawCharacter(g);   
                drawEnemy(g);
                drawGunMag(g); 
                drawHP(g); 
            }

            public void drawCharacter(Graphics g)
            {
                g.setColor(Color.blue);
                //ag.fillArc(mapMainCharX, mapMainCharY, 35, 35, 30, 300);
                g.drawImage(charImage, battleCharX, battleCharY, this);
            }

            public void drawEnemy(Graphics g)
            {
                g.setColor(Color.blue);
                g.drawImage(scientist, eX, eY, this); 
            }

            public void drawGunMag(Graphics g)
            {
                g.setFont(new Font("Helvetica", Font.BOLD, 36));
                g.setColor(Color.black);
                g.drawString("" + gunMag, 850, 50);
                g.setFont(new Font("Helvetica", Font.BOLD, 36));
                g.setColor(Color.black);
                g.drawString("| " + totalAmmo, 900, 50);
            }

            public void drawHP(Graphics g)
            {
                //MY HP
                g.setColor(Color.black);
                g.setFont(new Font("Helvetica", Font.BOLD, 20));
                g.drawString("MH", 10, 30);
                g.setColor(Color.red);
                g.fillRect (50, 10, maxHP, 20);
                g.setColor(Color.green); 
                g.fillRect (50, 10, hp, 20);    
                g.setColor(Color.white);
                // enemy HP
                g.setFont(new Font("Helvetica", Font.BOLD, 20));
                g.setColor(Color.red);
                g.fillRect (450-75*level, 460, 500+75*level, 20);
                g.setColor(Color.green); 
                g.fillRect (950-enemyHP, 460, enemyHP, 20);
                g.setColor(Color.black);
                g.setFont(new Font("Helvetica", Font.BOLD, 20));
                g.drawString("CG", 960, 480);
            }

            public void keyTyped (KeyEvent e) {}

            public void keyPressed (KeyEvent e) 
            {
                int keyval = e.getKeyCode(); // get keyboard input 

                switch (keyval)
                {
                case KeyEvent.VK_A: // pressed left 
                    battleCharX -= 5 ; // go left
                    left = true; // facing left
                    if(gun)
                        charImage = leftWalkGun; 
                    else 
                        charImage = leftWalk;
                    break;
                case KeyEvent.VK_D: // pressed right
                    battleCharX += 5 ;// go right
                    left = false; // not facing left
                    if(gun)
                        charImage = rightWalkGun; 
                    else 
                        charImage = rightWalk;
                    break; 
                case KeyEvent.VK_W: // pressed up
                    battleCharY -= 5 ;// go up
                    break;
                case KeyEvent.VK_S: // pressed down
                    battleCharY += 5 ;// go down
                    break; 
                case KeyEvent.VK_R: // reload the Integral
                    if (left)
                        charImage = leftHeal;
                    else if (!left)
                        charImage = rightHeal; 
                    int bullets =  magCap - gunMag; // reload this amount of bullets
                    if (gunMag + totalAmmo < magCap)
                    {
                        gunMag += totalAmmo;
                        totalAmmo = 0; 
                    }
                    else if (totalAmmo > 0) // put all remaining bullets into Integral
                    {
                        gunMag = magCap;
                        totalAmmo -= bullets; 
                    } 

                    if (totalAmmo <= 0) // can't go below 0
                        totalAmmo = 0; 
                    break; 
                case KeyEvent.VK_H: // HEALING
                    if (left)
                        charImage = leftHeal;
                    else if (!left)
                        charImage = rightHeal; 
                    hp += (int)((50 + 15*iteration)*healPower);  // heal
                    healCount++;
                    if (hp >= maxHP) // heal to maxHP
                        hp = maxHP; 
                    if (healCount%10 == 0) // every 10 times take away 250pts
                    {
                        hp -= 250;
                        score -= (100+ 5*iteration);  
                        testScore();
                    }
                case KeyEvent.VK_C: // Special Calc Ability
                    if(tiInspire.isSelected()) // Partial Fraction Decomp
                    {
                        if (pfd == 0) // can't use if none exist
                            return;
                        
                        // set animation
                        if (left)
                            charImage = leftHeal;
                        else if (!left)
                            charImage = rightHeal; 
                        pfd--;  // use 1 item
                        enemyHP /= 2; // effect: -1/2 HP
                        score += 2500; // increase score
                    }
                    else if(wa.isSelected()) // Instant A+
                    {
                        if (apocalypse == 0)
                            return; 
                        //set Animation
                        if (left)
                            charImage = leftHeal;
                        else if (!left)
                            charImage = rightHeal; 
                        apocalypse--; // use up 1 item
                        score += 5000;
                        level ++; // increase level 
                        // reinitialize
                        enemyHP = 500+ level*75; 
                        battleCharX = 800;
                        battleCharY = 300;
                        // remove enemy on map, and return to map
                        enemyX.remove(enemyIndex);
                        enemyY.remove(enemyIndex);
                        BattleStats.setText(); 
                        cards.show(c, "Map"); 
                    }
                    break; 
                }

                outOfBounds(); ;
                repaint(); 
            }

            public void keyReleased (KeyEvent e) 
            {
                if (charImage == leftWalk)
                    charImage = leftStand; 
                else if (charImage == rightWalk)
                    charImage = rightStand; 
                else if (charImage == leftWalkGun)
                    charImage = leftStandGun; 
                else if (charImage == rightWalkGun)
                    charImage = rightStandGun; 
                else if(charImage == leftHeal)
                {
                    if (gun)
                        charImage = leftStandGun;
                    else
                        charImage = leftStand;
                }
                else if(charImage == leftHeal)
                {
                    if (gun)
                        charImage = rightStandGun;
                    else
                        charImage = rightStand;
                }

                repaint(); 
            }

            public void encounter(String atkType) // 50 * 72
            {
                if (atkType.equals("Sword")) // Derivative
                {
                    // get center of you and enemy 
                    int mainCore = battleCharX + 25;
                    int enemyCore = eX + 25;
                    int mainYAxis = battleCharX + 36;
                    int enemyYAxis = eY + 37; 
                    // if 120 units away to left or right, ask a question. Terms determined by level + difficulty of test. If question is answered correctly deal damage.
                    if (left) // face left
                    {
                        if ((Math.abs(mainCore - enemyCore) < 150) && (Math.abs(mainCore - enemyCore) > 0) && (Math.abs(mainYAxis - enemyYAxis) < 150) && (Math.abs(mainYAxis - enemyYAxis) > 0))
                        {
                            boolean correct = askQuestion(level + 5 + iteration); 
                            if (correct)
                            {
                                // deal dmg to opponent
                                enemyHP -= (150 + 20*iteration + atkBoost);
                                score += (150 + 20*iteration + atkBoost); 
                                if (ti84.isSelected()) // xtra damage ability ti84
                                {
                                    enemyHP -= 60; 
                                    score += 60;
                                }
                                else if (ti30.isSelected()) // xtra damage ability ti30
                                {
                                    int randomDamage = (int)Math.random()*10;
                                    enemyHP -= randomDamage;
                                }
                            }
                            else // if fail deal dmg to self
                            {
                                int playerDamage = (int) Math.random()*50 + 25 + atkBoost;
                                hp -= playerDamage;
                                score -= playerDamage; 
                                testScore();
                            }
                            charImage = leftStand;
                        }
                    }
                    else if (!left) // face right
                    {
                        if ((enemyCore - mainCore) < 120 && (enemyCore - mainCore) > 0 && (enemyYAxis - mainYAxis < 120) && (enemyYAxis - mainYAxis > 0))
                        {
                            boolean correct = askQuestion(level + 5 + iteration); 
                            if (correct)
                            {
                                enemyHP -= (150 + 20*iteration + atkBoost);
                                score += (150 + 20*iteration); 
                                if (ti84.isSelected())
                                {
                                    enemyHP -= 60; 
                                    score += 60;
                                }
                                else if (ti30.isSelected())
                                {
                                    int randomDamage = (int)Math.random()*10;
                                    enemyHP -= randomDamage;
                                }
                            }
                            else
                            {
                                int playerDamage = (int) Math.random()*50 + 25 + atkBoost;
                                hp -= playerDamage;
                                score -= playerDamage; 
                                testScore();
                                if (ti84.isSelected())
                                {
                                    hp -= 60; 
                                    score -= 60;
                                    testScore();
                                }
                                else if (ti30.isSelected())
                                {
                                    int randomDamage = (int)Math.random()*10;
                                    hp -= randomDamage;
                                    score -= randomDamage; 
                                    testScore();
                                }
                            }
                            charImage = rightStand; 
                        }
                    }
                }

                else if (atkType.equals("Gun"))
                {
                    int atkLine = battleCharY + 36;
                    if (left) // facing left 
                    {
                        if (atkLine>= eY && atkLine<= (eY+72) && eX < battleCharX)
                        {
                            boolean correct = askQuestion(level + 2 + iteration); 
                            if (correct)
                            {
                                enemyHP -= (50 + 10*iteration + atkBoost); 
                                score += (50 + 5*iteration + atkBoost);
                                if (ti89.isSelected())
                                {
                                    enemyHP -= 60; 
                                    score += 60;
                                }
                                else if (ti30.isSelected())
                                {
                                    int randomDamage = (int)Math.random()*10;
                                    enemyHP -= randomDamage;
                                }
                            }
                            else
                            {
                                int playerDamage = (int) Math.random()*25 + 25;
                                hp -= playerDamage;
                                score -= playerDamage;
                                testScore();
                                if (ti89.isSelected())
                                {
                                    hp -= 60; 
                                    score -= 60;
                                    testScore();
                                }
                                else if (ti30.isSelected())
                                {
                                    int randomDamage = (int)Math.random()*10;
                                    hp -= randomDamage;
                                    score -= randomDamage; 
                                    testScore();
                                }
                            }
                            charImage = leftStandGun; 
                        }
                    }
                    else if (!left) // facing right
                    {
                        if (atkLine>= eY && atkLine<= (eY+72) && eX > battleCharX)
                        {
                            boolean correct = askQuestion(level + 2 + iteration); 
                            if (correct)
                            {
                                enemyHP -= (50 + 10*iteration); 
                                score += (10 + 5*iteration); 
                                if (ti89.isSelected())
                                {
                                    enemyHP -= 60; 
                                    score += 60;
                                }
                                else if (ti30.isSelected())
                                {
                                    int randomDamage = (int)Math.random()*10;
                                    enemyHP -= randomDamage;
                                }
                            }
                            else
                            {
                                int playerDamage = (int) Math.random()*25 + 25;
                                hp -= playerDamage;
                                score -= playerDamage; 
                                testScore();
                                if (ti89.isSelected())
                                {
                                    hp -= 60; 
                                    score -= 60;
                                    testScore();
                                }
                                else if (ti30.isSelected())
                                {
                                    int randomDamage = (int)Math.random()*10;
                                    hp -= randomDamage;
                                    score -= randomDamage; 
                                    testScore();
                                }
                            }
                            charImage = rightStandGun; 
                        }
                    }
                }

                else if (atkType.equals("Enemy"))
                {
                    // Xaxis
                    int mainCore = battleCharX + 28;
                    int enemyCore = eX + 27;
                    // Yaxis
                    int mainAxis = battleCharY + 37;
                    int enemyAxis = eY + 39;
                    // differential
                    int dx = mainCore - enemyCore;
                    int dy = mainAxis - enemyAxis; 
                    // if within 40 pixels in any direction 
                    if (Math.pow(dx, 2) + Math.pow(dy, 2) <= 1600)
                        hp -= 5;  
                }
                
                BattleStats.setText(); 
                ((MapPanel) map).updateText();
                
                if(hp <= 0)
                {
                    hp = maxHP; 
                    enemyHP = 500+ level*75; 
                    battleCharX = 800;
                    battleCharY = 300; 
                    returnFromBattle();
                    fullMenu.setVisible(false); 
                    cards.show(c, "Game Over");
                }

                if (enemyHP <= 0)
                {
                    level ++;
                    score += 1000;  
                    enemyHP = 500+ level*75; 
                    battleCharX = 800;
                    battleCharY = 300; 
                    enemyX.remove(enemyIndex);
                    enemyY.remove(enemyIndex);
                    returnFromBattle(); 
                    cards.show(c, "Map"); 
                }
            }

            public void mouseClicked(MouseEvent e) {}

            public void mousePressed(MouseEvent e) 
            {
                // Right Mouse Button = Derivative 
                if(e.isMetaDown())
                {
                    gun = false;
                    combo++;
                    if (left)
                    {
                        switch (combo%4)
                        {
                        case 1: charImage = leftAtk; break;
                        case 2: charImage = leftAtk2; break;
                        case 3: charImage = leftAtk3; break; 
                        default: charImage = leftAtk4; break; 
                        } 
                    }

                    else
                    {
                        switch (combo%4)
                        {
                        case 1: charImage = rightAtk; break;
                        case 2: charImage = rightAtk2; break;
                        case 3: charImage = rightAtk3; break; 
                        default: charImage = rightAtk4; break; 
                        } 
                    }

                    encounter("Sword");
                } 

                // Left Mouse Button = Integral 
                else 
                {
                    gun = true; 

                    if(gunMag <= 0) 
                        return; 
                    else
                    {
                        gunMag--; 
                        if (left)
                            charImage = leftShoot; 
                        else 
                            charImage = rightShoot; 
                    }

                    encounter("Gun");                       
                }   
                
                repaint();
            }

            public void mouseReleased(MouseEvent e) 
            {
                if (charImage == leftAtk || charImage == leftAtk2 || charImage == leftAtk3 || charImage == leftAtk4)
                    charImage = leftStand; 
                else if (charImage == rightAtk || charImage == rightAtk2 || charImage == rightAtk3 || charImage == rightAtk4)
                    charImage = rightStand; 
                else if (charImage == leftShoot)
                    charImage = leftStandGun; 
                else if (charImage == rightShoot)
                    charImage = rightStandGun; 
                repaint(); 
            }

            public void mouseEntered(MouseEvent e) {}

            public void mouseExited(MouseEvent e) {}

            public void focusGained(FocusEvent e) {
                enemyMove.start(); 
            }

            public void focusLost(FocusEvent e) {
                enemyMove.stop(); 
            }

            public void actionPerformed(ActionEvent e) {
                switch (randomMovement)
                {
                case 0: eX += 5; break; // right
                case 1: eX -= 5; break; // left
                case 2: eY += 5; break; // up
                case 3: eY -= 5; break; // down
                case 4: eX += 5; eY += 5; break; // NE
                case 5: eX += 5; eY -= 5; break; // SE
                case 6: eX -= 5; eY += 5; break; // NW
                case 7: eX -= 5; eY -= 5; break; // SW
                }
                
                if (eX <= 5)
                    eX = 5;
                else if (eX >= 960)
                    eX = 960;   
                if (eY <= 5)
                    eY = 5;
                else if (eY >= 380)
                    eY = 380; 
                if (eX == 5 || eX == 960 || eY == 5 || eY == 380)
                    randomMovement = (int)(Math.random()*8);
                encounter("Enemy"); 
                repaint();
            }
            
            public void outOfBounds()
            {
                if (battleCharX <= 5)
                    battleCharX = 5;
                else if (battleCharX >= 960)
                    battleCharX = 960;  
                if (battleCharY <= 5)
                    battleCharY = 5;
                else if (battleCharY >= 380)
                    battleCharY = 380; 
            }   
            
        }

    }

    class InstructionPanel extends JPanel implements ActionListener
    {
        TextArea info;
        JButton gameReturn;
        String instructionText;
        boolean dir; 

        public InstructionPanel(boolean direction)
        {
            dir = direction; 
            this.setLayout(new BorderLayout());

            instructionText = new String
            (           
                    "The Story So Far...\n\n"+ 
                    
                    "As Sephiroth, you are a Calculus student who excels at Derivatives and Integrals." +
                    "Unfortunately, when it was time for you to take the SAT's, you failed horribly."+
                    "In the math section, you only got 200 points. Surely, you can do better than that." +
                    "As punishment, CollegeBoard banished you to a remedial math school, where you will be" + 
                    "doomed to learn math all over again. Today you will be taking a test to evaluate" +
                    "your math skills. The school is strict; passing the test is a team effort. If one of your" + 
                    "peers cannot pass the test, everyone fails the test. Realizing how bad these students actually are," +
                    "you decide to provide a little extra \"help\" to your students.\n\n" +  
                    "To accomplish this task, you snuck in your Calculus Concepts: Derivative and Integral." +
                    "Using these two mathematical Concepts, you will easily teach the students a lesson or two." + 
                    "Your friend snuck into the school with you to provide you with the basic necessities to keep going..." + 
                    "To prove yourself worthy to CollegeBoard, you need to take the following tests: SAT I Math, SAT II Math Level I, SAT II Math Level 2," + 
                    "ACT, AP Calculus AB, AP Calculus BC, and Calculus D.\n\n" +
                    
                    "BASIC CONTROLS\n\n" +

                    "Movement:\n" +
                    "'W' - Move Up\n" +
                    "'A' - Move Left\n" +
                    "'S' - Move Right\n" +
                    "'D' - Move Down\n\n" +
                    
                    "TUTORING GAMEPLAY\n" +
                    "You start out in a hallway. Seeing as all of you need to pass in order to leave the school," +
                    "You need to help the students cheat for their math test before proceeding to take the math test yourself." +  
                    "When you encounter a student, he will take you into a classroom for a private \"study session\"." +
                    "The use of calculators in a non-classroom environment is prohibited." +
                    "In these private classrooms you can freely use your tools. After helping a student reach full understanding " +
                    "by reducing their Complexity Gauge to 0, your students will pay you in cash. \n\n" +

                    "Primary Means of Exhibiting Proficiency in Math \n\n" +

                    "Your primary Concepts are the Derivative and the Integral.\n\n" + 

                    "The Integral is one of the most fundamental Concepts of Calculus. " +
                    "Sephiroth can use the Integral from afar and perform successful integration. However, integration tends to be messy. " + 
                    "At rather inconvenient times, his integral tends to be hard to find. " +
                    "It is then that you need to take an integral once again to keep performing successful integrals. " +
                    "Don't expect integration to be smooth. Every time you extend your limits of integration, the integral " +
                    "gets messier. Keep an eye on how many times you can perform an integral, it is possible to reach the point " +
                    "where Sephiroth will be so bamboozled that he cannot perform integrals anymore. " +
                    "Every time a student understands how to take an integral, you will be able to regain your mental state. " +
                    "Thus you will be able to perform more integrals. You can also purchase Tylenol at the blackmarket store to " + 
                    "replenish your mental state after a study session.\n\n" + 

                    "On the other hand, the Derivative is rather limited. It can only measure change in a small dX. " +
                    "As simple as your differentiation may be, the Derivative is one of the most widely used concepts " +
                    "of Calculus in everyday life. It is also one of the most vital of skills. While you may not be able " +
                    "to perform an integral, you will always be able to take a derivative. Derivatives are easy and clean." +
                    "Unlike the integral functions where integrals may not always be easy to take, the derivatives here are extremely easy to take," +
                    "for the functions you encounter are nice and differentiable. " +
                    "Even though the derivative might require more intimacy with the student, it will always have a greater effect in your study sessions than integrals.\n\n" + 
                    
                    "When using the Integral or the Derivative for teaching purposes, you will have to answer a simple math question. " +
                    "The math question is rather simple, you just have to think in Java Algebra. The number of terms increases as time goes on. " +
                    "The students here aren't taught with the traditional Order of Operations. Teach as they have learned, in sequential fashion, with utter disregard to the " +
                    "sacred Order of Operations. " +
                    "The students are nice enough to provide you with all the time you need to answer their questions. Even the teachers will." +
                    "Fail, and your tutees or teacher will severly reprimand you.\n\n" +
                    
                    "Left Mouse Button - Perform an Integral\n" +
                    "Right Mouse Button - Take a Derivative.\n" + 
                    "'R' - Extension of the Limits of Integration.\n\n" +

                    "Calculators:\n\n" +

                    "Calculators provide you with an edge in the heat of study sessions. " +
                    "Some calculators give you a chance to truly show off your math skills. " +
                    "As you progress through the game, you will gain more money to purchase better calculators.\n\n" +

                    "Here is a list of Calculators and their abilities and status gains:\n\n" +
                    
                    "TI_30XII-A: nPr nCr (+30 Math, default Calculator)\n" + 
                    "TI_84-Plus_Silver_Edition: nDeriv (+60 Math, additional + 60 ATK on Derivative)\n" +
                    "TI_89-Titanium: fNINT (+ 60 Math, additional +60 Math on Integral)\n" +
                    "TI_Inspire_Plus: Partial Fraction Decomposition (+100 Math)\n" +
                    "WolframAlpha: Instant A+ (+150 Math)\n\n" +
                    
                    "Abilities\n\n"+
                    
                    "nPr and nCr is the pick and choose ability. An additional amount of up to 10 understanding may be inflicted on your opponent \n\n" +
                    "Partial Fraction Decomposition is a powerful move that allows students/teachers to instantly understand half the material you give them. " +
                    "They might be confused as to what you have to say for 5 seconds or so.\n\n" +
                    "WolframAlpha is the most sophisticated computational engine of this day an age. Use it and students/teachers will gain complete understanding\n" +
                    "For the TI-Inspire and WolframAlpha, you may activate the special ability by using 'C'.\n\n" + 

                    "Headphones:\n\n" +

                    "Music calms the mind and soul. Headphones help restore your mental state, represented by MH (Mental Health) " +
                    "With better headphones, you can be more in tune with the beat, and you can heal better. "+
                    "However, don't abuse the power of music. Too much music can hurt your ears, and might even cause you discomfort. \n\n" +

                    "Here is a list of Headphones and their amplification specifications: \n" +
                    "FMJ by SkullCandy (Healing *1.0, default Headphones) \n" +
                    "Hesh by Paul Frank (Healing *1.5) \n" +
                    "G.I. by SkullCandy (Healing *2.0) \n" +
                    "Aviators by ROCNation (Healing* 2.5) \n" +
                    "Harajuku Lovers by Monster (Healing *3.0) \n" +
                    "HeartBeats by Lady Gaga (Healing *3.5) \n" +
                    "Beats by Dr. Dre (Healing *4.0) \n\n" +
                    
                    "All upgrades to headphones will increase your mental health by 25 MH \n\n"
            );

            info = new TextArea("", 0, 0, info.SCROLLBARS_VERTICAL_ONLY); // only have a vertical scrollbar
            info.setText(instructionText);
            add(info, BorderLayout.CENTER); // add to CENTER.
            info.setEditable(false); // don't allow users to edit the TextArea. 
            this.add(info);

            if (dir == true)
                gameReturn = new JButton("Return To Game");
            else if (dir == false)
                gameReturn = new JButton("Return to Start Menu");
            gameReturn.addActionListener(this);
            this.add(gameReturn, BorderLayout.SOUTH);
        }

        public void actionPerformed(ActionEvent evt)
        {
            String command = evt.getActionCommand();

            if (command.equals("Return To Start Menu"))
                cards.show(c, "Start");     
            else
            {
                fullMenu.setVisible(true); 
                ((MapPanel)map).updateText();
                ((BattlePanel)battlefield).updateText(); 
                cards.show(c, "Map");   
            }
        }

    }


    class ExaminationPanel extends JPanel  
    {
        ExamBattlePanel arena; 
        ContainerPanel contain;
        Image charImage, boss; 
        Image rightStand, rightWalk;
        Image rightWalkGun, rightStandGun;
        Image rightAtk, rightShoot, rightFire, rightHeal, rightCalc; 
        int enemyHP; 

        public ExaminationPanel()
        {
            setLayout(new BorderLayout()); 
            enemyHP = 900; 

            // Stand/Walk for The Derivative
            rightWalk = getImage(getDocumentBase(), "sephiroth_right_walking.gif");
            WaitForImage (this, rightWalk, 1);
            rightStand = getImage(getDocumentBase(), "sephiroth_right_standing.gif");
            WaitForImage (this, rightStand, 2);

            // Stand/Walk for The Integral
            rightWalkGun = getImage(getDocumentBase(), "sephiroth_right_walking_gun.gif");
            WaitForImage (this, rightWalkGun, 3);
            rightStandGun = getImage(getDocumentBase(), "sephiroth_right_standing_gun.gif");
            WaitForImage (this, rightStandGun, 4);

            // Math Images
            rightAtk= getImage(getDocumentBase(), "sephiroth_right_atk1.gif");
            WaitForImage (this, rightAtk, 5);
            rightShoot = getImage(getDocumentBase(), "sephiroth_right_shoot.gif");
            WaitForImage (this, rightShoot, 6);
            rightFire = getImage(getDocumentBase(), "sephiroth_right_fire.gif");
            WaitForImage (this, rightFire, 7);
            rightHeal = getImage(getDocumentBase(), "sephiroth_right_healing.gif");
            WaitForImage(this, rightHeal, 8);
            rightCalc = getImage(getDocumentBase(), "sephiroth_right_calc.gif");
            WaitForImage(this, rightCalc, 9);
            charImage = rightShoot; 


            // Boss Image
            boss = getImage(getDocumentBase(), "boss.jpg");
            WaitForImage(this, boss, 10);

            arena = new ExamBattlePanel(); 
            this.add(arena, BorderLayout.CENTER);
            contain = new ContainerPanel(); 
            this.add(contain, BorderLayout.SOUTH); 
        }

        class ExamBattlePanel extends JPanel
        {
            Image background; 

            ExamBattlePanel()
            {
                background = getImage(getDocumentBase(), "math_classroom.jpg");
                WaitForImage (this, background, 1);
            }

            public void paintComponent(Graphics g)
            {
                super.paintComponent(g);
                g.drawImage(background, 0, 0, this); 
                g.drawImage(charImage, 100, 100,this);
                g.drawImage(boss, 750, 100, this);
                drawGunMag(g);
                drawHP(g); 
            }

            public void drawGunMag(Graphics g)
            {
                g.setFont(new Font("Helvetica", Font.BOLD, 36));
                g.setColor(Color.white);
                g.drawString("" + gunMag, 850, 50);
                g.setFont(new Font("Helvetica", Font.BOLD, 36));
                g.setColor(Color.white);
                g.drawString("| " + totalAmmo, 900, 50);
            }

            public void drawHP(Graphics g)
            {
                g.setColor(Color.white);
                g.setFont(new Font("Helvetica", Font.BOLD, 20));
                g.drawString("MH", 10, 30);
                g.setColor(Color.red);
                g.fillRect (50, 10, maxHP, 20);
                g.setColor(Color.green); 
                g.fillRect (50, 10, hp, 20);    
                g.setColor(Color.white);
                g.setFont(new Font("Helvetica", Font.BOLD, 20));
                g.setColor(Color.red);
                g.fillRect (50, 380, 900, 20);
                g.setColor(Color.green); 
                g.fillRect (950-enemyHP, 380, enemyHP, 20);
                g.setColor(Color.white);
                g.setFont(new Font("Helvetica", Font.BOLD, 20));
                g.drawString("CG", 960, 400);
            }
        }

        class ContainerPanel extends JPanel
        {
            CommandPanel playerCommand;
            StatsPanel statistics; 
            
            ContainerPanel()
            {
                setLayout(new GridLayout(1, 2));
                playerCommand = new CommandPanel();
                this.add(playerCommand);
                
                statistics = new StatsPanel();
                this.add(statistics); 
            }
        }
        
        class CommandPanel extends JPanel implements ActionListener
        {
            JLabel commandCenter;
            ButtonGroup commandList; 
            JRadioButton derivativeCMD, integralCMD, reloadCMD, headphonesCMD, pfdCMD, apocalypseCMD;  
            JButton doCommand; 
            int selected, enemyDamage;

            public CommandPanel()
            {
                this.setLayout(new GridLayout(8,1));

                commandCenter = new JLabel("Command Prompt CMD.exe");
                this.add(commandCenter);

                // CMD list 
                commandList = new ButtonGroup(); 

                integralCMD = new JRadioButton("Integral");
                integralCMD.addActionListener(this);
                integralCMD.setSelected(true); 
                commandList.add(integralCMD);
                this.add(integralCMD);

                reloadCMD = new JRadioButton("Manual Reload");
                reloadCMD.addActionListener(this);
                commandList.add(reloadCMD);
                this.add(reloadCMD);

                derivativeCMD = new JRadioButton("Derivative");
                derivativeCMD.addActionListener(this);
                commandList.add(derivativeCMD);
                this.add(derivativeCMD);

                headphonesCMD = new JRadioButton("Headphones");
                headphonesCMD.addActionListener(this);
                commandList.add(headphonesCMD);
                this.add(headphonesCMD);
                
                pfdCMD = new JRadioButton("Partial Fraction Decomposition");
                pfdCMD.addActionListener(this);
                pfdCMD.setEnabled(false);
                commandList.add(pfdCMD);
                this.add(pfdCMD);
                
                apocalypseCMD = new JRadioButton("Instant A+");
                apocalypseCMD.addActionListener(this);  
                commandList.add(apocalypseCMD);
                apocalypseCMD.setEnabled(false);
                this.add(apocalypseCMD);

                doCommand = new JButton("Execute Command");
                doCommand.addActionListener(this);
                this.add(doCommand);
            }

            public void actionPerformed(ActionEvent evt)
            {
                String command = evt.getActionCommand();
                
                if (derivativeCMD.isSelected())
                    charImage = rightAtk; 
                else if (integralCMD.isSelected())
                    charImage = rightShoot; 
                else if (reloadCMD.isSelected())
                    charImage = rightStand; 
                else if (headphonesCMD.isSelected())
                    charImage = rightHeal; 
                else if (pfdCMD.isSelected())
                    charImage = rightHeal; 

                if(command.equals("Execute Command"))
                {
                    if (derivativeCMD.isSelected())
                    {
                        boolean correct = askQuestion(6 + iteration); 

                        if (correct)
                        {
                            int playerDamage = (int) Math.random()*25 + 75 + atkBoost;
                            enemyHP -= playerDamage;
                            score += playerDamage; 
                            if (ti84.isSelected())
                            {
                                enemyHP -= 60; 
                                score += 60;
                            }
                            else if (ti30.isSelected())
                            {
                                int randomDamage = (int)Math.random()*10;
                                enemyHP -= randomDamage;
                            }
                        }
                        else
                        {
                            int playerDamage = (int) Math.random()*25 + atkBoost/2;
                            hp -= playerDamage;
                            score -= playerDamage; 
                            testScore();
                        }

                        enemyDamage = (int) Math.random()*49 + 1;
                        hp -= enemyDamage;  
                    }

                    else if (integralCMD.isSelected())
                    {
                        if (gunMag <= 0)
                        {
                            reloadCMD.setSelected(true); 
                            integralCMD.setEnabled(false);
                            gunMag = 0; 
                            enemyDamage = (int) Math.random()*50 + 50;
                            hp -= enemyDamage;  
                        }
                        else
                        {
                            gunMag--; 
                            boolean correct = askQuestion(12 + 2*iteration); 
                            if (correct)
                            {
                                int playerDamage = (int) Math.random()*75 + 125;
                                enemyHP -= playerDamage;
                                score += playerDamage; 
                                if (ti89.isSelected())
                                {
                                    enemyHP -= 60; 
                                    score += 60;
                                }
                                if (ti30.isSelected())
                                {
                                    int randomDamage = (int)Math.random()*10;
                                    enemyHP -= randomDamage;
                                }
                            }
                            else
                            {
                                int playerDamage = (int) Math.random()*25 + 50;
                                hp -= playerDamage;
                                score -= playerDamage; 
                                if (ti89.isSelected())
                                {
                                    hp -= 60; 
                                    score -= 60;
                                }
                                else if (ti30.isSelected())
                                {
                                    int randomDamage = (int)Math.random()*10;
                                    hp -= randomDamage;
                                    score -= randomDamage; 
                                }
                            }
                            enemyDamage = (int) Math.random()*50 + 50;
                            hp -= enemyDamage; 
                        }
                    }
                    else if (reloadCMD.isSelected()) 
                    {
                        if (totalAmmo == 0)
                        {
                            derivativeCMD.setSelected(true);
                            reloadCMD.setEnabled(false); 
                        }
                        enemyDamage = (int) Math.random()*25+ 25;
                        hp -= enemyDamage;  
                        reload(); 
                    }
                    else if (headphonesCMD.isSelected())
                    {
                        hp += (int)((50 + 10*iteration)*healPower); 
                        healCount++;
                        if (hp >= maxHP)
                            hp = maxHP; 
                        if (healCount%10 == 0)
                        {
                            hp -= 250;  
                            score -= 500; 
                            testScore();
                        }
                        if (healCount%4 == 0)
                        {
                            enemyDamage = (int) Math.random()*50 + 50;
                            hp -= enemyDamage;  
                        }        
                    }
                    else if (pfdCMD.isSelected())
                    {
                        if (pfd <= 0)
                            return;
                        pfd--;
                        if (pfd <= 0)
                        {
                            derivativeCMD.setSelected(true);
                            pfdCMD.setEnabled(false);
                        }
                        enemyHP /= 2; 
                        enemyDamage = (int) Math.random()*50 + 50;
                        hp -= enemyDamage; 
                    }
                    else if (apocalypseCMD.isSelected())
                    {
                        if (apocalypse <= 0)
                            return;
                        apocalypse--;
                        if (apocalypse <= 0)
                        {
                            derivativeCMD.setSelected(true);
                            apocalypseCMD.setEnabled(false);
                        }
                        enemyHP = 0; 
                        enemyDamage = (int) Math.random()*50 + 50;
                        hp -= enemyDamage; 
                    }
                }
                
                updateText();
                
                if(hp <= 0)
                {
                    hp = maxHP; 
                    enemyHP = 950;
                    fullMenu.setVisible(false);
                    cards.show(c, "Game Over");
                }
                
                if (enemyHP <= 0)
                {
                    enemyHP = 900; // Reset enemy HP
                    score += 5000;  // Gain $5000
                    iteration++; // Next Test
                    configureTest(); // Get test info
                    initializeMaximum(); // set new maximums
                    // set all stats to maximum
                    hp = maxHP;
                    gunMag= magCap;
                    totalAmmo = maxAmmo;
                    // reset enemies
                    level = 0; 
                    initializeEnemies();
                    // switch to Start panel, and update all statuses.
                    cards.show(c, "Start");
                    ((StartPanel)startPage).updateIteration(); 
                    ((MapPanel)map).updateText(); 
                }

                repaint(); 
            }

            public void reload()
            {
                int bullets =  magCap - gunMag;
                if (gunMag + totalAmmo < magCap)
                {
                    gunMag += totalAmmo;
                    totalAmmo = 0; 
                }
                else if (totalAmmo > 0)
                {
                    gunMag = magCap;
                    totalAmmo -= bullets; 
                }

                if (totalAmmo <= 0)
                    totalAmmo = 0;  
            }
        }
        
        // These methods allow external panels to update this panel's information.
        public void enablePFD() // If the TI-Inspire is used, allow the PFD to be used.
        {
            if (calcPower >= 4)
                contain.playerCommand.pfdCMD.setEnabled(true);
        }   
        public void disablePFD()
        {
            contain.playerCommand.pfdCMD.setEnabled(false);
        }
        public void enableIA()
        {
            if (calcPower == 5)
                contain.playerCommand.apocalypseCMD.setEnabled(true);
        }
        public void disableIA()
        {
            contain.playerCommand.apocalypseCMD.setEnabled(false);
        }
        public void updateText()
        {
            contain.statistics.setText();
        }

    }

    class StatsPanel extends JPanel
    {
        JTextField text1, text3, text4, text5, text6, text7; 
        JLabel label1, label3, label4, label5, label6, label7; 

        StatsPanel()
        {
            this.setBackground(Color.BLACK); 
            this.setLayout(new GridLayout(6,2)); 
            label1 = new JLabel("Test"); 
            add(label1); 
            text1 = new JTextField(mathTest); 
            add(text1);
            label3 = new JLabel("Cash"); 
            add(label3); 
            text3 = new JTextField("0");
            add(text3);
            label4 = new JLabel("Instant A+"); 
            add(label4); 
            text4 = new JTextField("0");
            add(text4); 
            label5 = new JLabel("Partial Fraction Decomposition"); 
            add(label5); 
            text5 = new JTextField("0");
            add(text5);
            label6 = new JLabel("Calculator"); 
            add(label6); 
            text6 = new JTextField(calculator);
            add(text6); 
            label7 = new JLabel("Headphones"); 
            add(label7); 
            text7 = new JTextField(headphones);
            add(text7);
        }

        void setText() 
        {
            text1.setText(mathTest); 
            text3.setText(score + ""); 
            text4.setText(pfd + ""); 
            text5.setText(apocalypse + ""); 
            text6.setText(calculator);
            text7.setText(headphones);
        }
    }

    class GameOverPanel extends JPanel implements ActionListener
    {
        Image gameOverBackground;
        JButton resume, quit; 

        GameOverPanel()
        {
            setLayout(null); 
            gameOverBackground = getImage(getDocumentBase(), "game_over.jpg");
            WaitForImage(this, gameOverBackground, 1);

            resume = new JButton("Resume Game");
            resume.setBounds(600, 50, 200, 50);
            resume.addActionListener(this);
            this.add(resume);

            quit = new JButton("Quit Game");
            quit.setBounds(600, 150, 200, 50);
            quit.addActionListener(this);
            this.add(quit); 
        }

        public void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            g.drawImage(gameOverBackground, 0, 0, this);
        }

        public void actionPerformed(ActionEvent e) 
        {
            String command = e.getActionCommand(); 

            // Take a penalty of 10,000 pts. 
            score -= 10000;
            testScore();
            mapMainCharX = 500;
            mapMainCharY = 300;

            if (command.equals("Resume Game"))
            {
                fullMenu.setVisible(true);  
                cards.show(c, "Map");
            }
            else if (command.equals("Quit Game"))
            {
                cards.show(c, "Start"); 
            }
        }

    }

    class StatusPanel extends JPanel implements ActionListener
    {
        JButton backToMap; 
        Image fmj, hesh, gi, aviator, harajuku, heartbeat, beats;
        Image ti30xiis, ti84plus, ti89titanium, tiinspirecas, wolframalpha; 
        
        StatusPanel()
        {
            setLayout(new BorderLayout());
            setBackground(Color.black); 
            backToMap = new JButton("Return to Map"); 
            backToMap.addActionListener(this);
            this.add(backToMap, BorderLayout.SOUTH);
            
            fmj= getImage(getDocumentBase(), "fmj.jpg");
            WaitForImage (this, fmj, 1);
            hesh= getImage(getDocumentBase(), "hesh.jpg");
            WaitForImage (this, hesh, 2);
            gi= getImage(getDocumentBase(), "gi.jpg");
            WaitForImage (this, gi, 3);
            aviator= getImage(getDocumentBase(), "aviator.jpg");
            WaitForImage (this, aviator, 4);
            harajuku= getImage(getDocumentBase(), "harajuku.jpg");
            WaitForImage (this, fmj, 5);
            heartbeat= getImage(getDocumentBase(), "heartbeats.jpg");
            WaitForImage (this, heartbeat, 6);
            beats= getImage(getDocumentBase(), "beats.jpg");
            WaitForImage (this, beats, 7);
            ti30xiis= getImage(getDocumentBase(), "ti30xiis.jpg");
            WaitForImage (this, ti30xiis, 8);
            ti84plus= getImage(getDocumentBase(), "ti84plus.jpg");
            WaitForImage (this, ti84plus, 9);
            ti89titanium= getImage(getDocumentBase(), "ti89titanium.jpg");
            WaitForImage (this, ti89titanium, 10);
            tiinspirecas= getImage(getDocumentBase(), "tiinspire.jpg");
            WaitForImage (this, tiinspirecas, 11);
            wolframalpha= getImage(getDocumentBase(), "wolphramalpha.jpg");
            WaitForImage (this, beats, 12);
        }
        
        public void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            g.setFont(new Font("Helvetica", Font.BOLD, 30));
            g.setColor(Color.white); 
            g.drawString("Progress Report", 10, 40);
            g.setFont(new Font("Helvetica", Font.BOLD, 15));
            g.drawString("Class: " + mathTest, 10, 80);
            g.drawString("Tests Taken: "+ iteration, 10, 100);
            g.drawString("Students Taught: " + (iteration*6 + level), 10, 120);
            g.drawString("Times Healed: " + healCount, 10, 140);
            g.drawString("Cash: $" + score, 10, 160);
            g.drawString("Mental Health (MH): " + hp, 10, 180); 
            g.drawString("Maximum MH: " + (500 + iteration*25)+ " + " + (int)((healPower-1)*2)+"*25", 10, 200);
            g.drawString("Limits of Integration: " + gunMag + " | " + totalAmmo, 10, 220);
            g.drawString("Integration Capacity: " + magCap + " | " + maxAmmo, 10, 240);
            
            if(ti84.isSelected())
                g.drawString("Tutoring Proficiency (Derivative): " + (150+20*iteration)+ " + " + atkBoost + " Math Points (MP) + 60 Math Points (MP)", 10, 260);
            else
                g.drawString("Tutoring Proficiency (Derivative): " + (150+20*iteration)+ " + " + atkBoost + " Math Points (MP)" , 10, 260);
            
            if(ti89.isSelected())
                g.drawString("Tutoring Proficiency (Integral): " + (50 + 10*iteration) + " + " + atkBoost + " Math Points (MP) + 60 Math Points (MP)", 10, 280);
            else
                g.drawString("Tutoring Proficiency (Integral): " + (50 + 10*iteration) + " + " + atkBoost + " Math Points (MP)", 10, 280);
            
            g.drawString("Partial Fraction Decomposition: " + pfd, 10, 300);
            g.drawString("Instant A+: " + apocalypse, 10, 320); 
            g.drawString("Calculator: "+ calculator, 10, 340);
            g.drawString("Calculator Added Math Bonus: + " + atkBoost + " Math Points (MP)", 10, 360);
            g.drawString("Calculator Ability: " + calculatorAbility, 10, 380);
            g.drawString("Calculator Ability Description: " + calculatorAbilityDescription, 10, 400);
            g.drawString("Headphones: " + headphones, 10, 420);
            g.drawString("Headphones Amplification: X " + healPower, 10, 440); 
            g.drawString("Headphones MH Bonus: + "+ ((healPower-1)*50) +" MH", 10, 460);
            
            g.setFont(new Font("Helvetica", Font.BOLD, 20));
            g.drawString(headphones, 700, 375);
            
            switch((int)(healPower*2))
            {
            case 2: g.drawImage(fmj, 700, 400, 150, 150, this); break;
            case 3: g.drawImage(hesh, 700, 400, 150, 150, this); break;
            case 4: g.drawImage(gi, 700, 400, 150, 150, this); break;
            case 5: g.drawImage(aviator, 700, 400, 150, 150, this); break;
            case 6: g.drawImage(harajuku, 700, 400, 150, 150, this); break;
            case 7: g.drawImage(heartbeat, 700, 400, 150, 150, this); break;
            case 8: g.drawImage(beats, 700, 400, 150, 150, this); break;
            }
            
            g.drawString(calculator, 700, 75); 

            if(ti30.isSelected())
                 g.drawImage(ti30xiis, 700, 100, 150, 200, this);
            else if (ti84.isSelected())
                 g.drawImage(ti84plus, 700, 100, 150, 200, this);
            else if (ti89.isSelected())
                 g.drawImage(ti89titanium, 700, 100, 150, 200, this);
            else if (tiInspire.isSelected())
                g.drawImage(tiinspirecas, 700, 100, 150, 200, this);
            else if (wa.isSelected())
                g.drawImage(wolframalpha, 700, 100, 150, 150, this);
        }

        public void actionPerformed(ActionEvent e) 
        {
            String command = e.getActionCommand();
            if (command.equals("Return to Map"))
                cards.show(c, "Map");
        }
    }
    
    class BasicFunction
    {
        int partNum;
        int[] parts;
        int[] signs; 

        BasicFunction(int terms)
        {
            parts = new int[terms];
            signs = new int[terms-1];

            for (int i = 0; i < parts.length; i++)
            {
                int randomPart = (int)(Math.random()*10);
                parts[i] = randomPart; 
            }

            for (int j = 0; j < signs.length; j++)
            {
                int randomSign = (int)(Math.random()*4 + 1);
                signs[j] = randomSign;
            }
        }

        String getRandomEquation()
        {
            String equation = "" + parts[0];

            for(int i = 0; i < signs.length; i++)
            {
                int operation = signs[i];

                switch (operation)
                {
                case 2: equation += (" - " + parts[i+1]); break; // Subtract
                case 3: equation += (" * " + parts[i+1]); break; // Multiply
                case 4: equation += (" / " + parts[i+1]); break; // Divide
                default: equation += (" + " + parts[i+1]); break; // Add
                }
            }

            return equation; 
        }

        String getAnswer()
        {
            int solution = parts[0]; 
            String answer= null;
            boolean indivisible = false; 

            for(int i = 0; i < signs.length; i++)
            {
                int operation = signs[i];
                switch (operation)
                {
                case 2: solution = solution - parts[i+1]; break; // Subtract
                case 3: solution = solution * parts[i+1]; break; // Multiply
                case 4: 
                    try
                    {
                        solution = solution / parts[i+1]; 
                    }
                    catch(ArithmeticException e)
                    {
                        indivisible = true; 
                    }
                break; // Divide
                default: solution = solution + parts[i+1]; break; // Add
                }
                if (indivisible)
                    break; 
            }

            if (indivisible)
                answer = "No Solution";
            else 
                answer = ""+solution;
            return answer; 
        }
    }
    
    public void prepareForBattle()
    {
        //can only escape in middle of battle.
        stat.setEnabled(false); 
        quit.setEnabled(false);
        escape.setEnabled(true);
        information.setEnabled(false);
        // can't access stores
        Store.setVisible(false);
        RetakeExam.setVisible(false);
    }
    
    public void returnFromBattle()
    {
        // return to normal settings
        stat.setEnabled(true); 
        quit.setEnabled(true);
        escape.setEnabled(false);
        information.setEnabled(true);
        Store.setVisible(true);
        RetakeExam.setVisible(true);
    }

    public boolean askQuestion(int terms)
    {
        BasicFunction equation = new BasicFunction(terms);
        String question = equation.getRandomEquation();
        String expectedAnswer = equation.getAnswer(); 
        String answer = JOptionPane.showInputDialog("What is the answer to "+ question + " ?" +
           "\n(Disregard Order of Operations. Use Java Algebra in sequential order." + 
           " \nIf at any time you divide by 0, your answer is \"No Solution\")");
        if (answer == null)
            return false;
        else if (answer.equals(expectedAnswer))
            return true; 
        else 
            return false; 
    }

    public void WaitForImage(JPanel component, Image image, int imgID)   
    {
        MediaTracker tracker = new MediaTracker ( component );

        try  
        {
            tracker.addImage(image, imgID);
            tracker.waitForID(0);
        }

        catch (InterruptedException e)   
        {
            e.printStackTrace();   
        }
    }

    public void determineTest()
    {
        switch(iteration)
        {
        case 0:
            mathTest = "SAT I Math";
        case 1:
            mathTest = "SAT II Math Level 1";
        case 2:
            mathTest = "SAT II Math Level 2";
        case 3:
            mathTest = "ACT";
        case 4:
            mathTest = "AP Calculus AB";
        case 5:
            mathTest = "AP Calculus BC";
        default:
            mathTest = "Calculus D";
        }
    }

    public void actionPerformed(ActionEvent evt) 
    {
        String command = evt.getActionCommand();
        
        if (command.equals("Current Progress Report"))
        {
            status.repaint();
            cards.show(c, "Status Report");
        }

        else if(command.equals("Quit Game"))
        {
           fullMenu.setVisible(false);
           cards.show(c, "Start");  
        }

        else if(command.equals("Ditch Study Session"))
        {
            ((BattlePanel) battlefield).initEnemy();
            returnFromBattle(); 
            mapMainCharX = 500;
            mapMainCharY = 300;
            cards.show(c, "Map");
        }
        
        else if(command.equals("Instructions"))
        {
            cards.show(c, "Reference");  
        }

        else if(command.equals("Buy Limits of Integration for $1000"))
        {
            if (score < 1000)
                return; 
            score -= 1000;
            totalAmmo = maxAmmo;    
        }
        else if(command.equals("Buy Tylenol for $2000"))
        {
            if (score < 2000)
                return;
            hp = maxHP;
            score -= 2000; 
        }
        
        else if (command.equals("Recharge P.F.D.  for $10000"))
        {
            if(score < 10000)
                return;
            pfd++;
        }
        
        else if (command.equals("Recharge Instant A+ for $25000"))
        {
            if(score < 25000)
                return;
            apocalypse++;
        }
        
        else if(command.equals(upgradeTextC))
        {
            if (score < upgradeCalcCost)
                return;
            calcPower++;
            score -= upgradeCalcCost; 
            upgradeCalcCost += 10000;
            upgradeTextC = "Upgrade Calculator for $" + upgradeCalcCost;
            if (calcPower >= 5)
            {
                upgradeCalc.setEnabled(false);
                upgradeTextC = "Calculator Fully Upgraded";
            }
            upgradeCalc.setText(upgradeTextC);

            switch(calcPower)
            {
            case 2: 
                ti84.setEnabled(true);
                initTI84(); 
                break;
            case 3:
                ti89.setEnabled(true);
                initTI89(); 
                break;
            case 4:
                tiInspire.setEnabled(true);
                initTIInspire(); 
                pfd++;
                buyPFD.setEnabled(true);
                tiInspire.setEnabled(true);
                break;
            case 5: 
                wa.setEnabled(true); 
                initWA(); 
                apocalypse++; 
                buyApocalypse.setEnabled(true);
                break; 
            }
        }

        else if(command.equals(upgradeTextH))
        {
            if (score < upgradeHeadphoneCost)
                return;
            healPower += 0.5;
            score -= upgradeHeadphoneCost; 
            upgradeHeadphoneCost += 1000;
            upgradeTextH = "Upgrade Headphones for $" + upgradeHeadphoneCost;
            if (healPower >= 4)
            {
                upgradeHeal.setEnabled(false);
                upgradeTextH = "Headphones Fully Upgraded";
            }
            upgradeHeal.setText(upgradeTextH);
            determineHeadphones(); 
            initializeMaximum(); 
        }

        else if(ti30.isSelected())
        {
            initTI30();
        }

        else if(ti84.isSelected())
        {
            initTI84();
        }

        else if(ti89.isSelected())
        {
            initTI89(); 
        } 

        else if(tiInspire.isSelected())
        {
            initTIInspire();
        } 

        else if(wa.isSelected())
        {
            initWA();
        } 
        
        else if(satI.isSelected())
        {
            iteration = 0; 
            initializeMaximum(); 
            mathTest = "SAT I Math"; 
            initializeEnemies();
        }
        
        else if(satII1.isSelected())
        {
            iteration = 1; 
            initializeMaximum(); 
            mathTest = "SAT II Math Level 1"; 
            initializeEnemies();
        }
        
        else if(satII2.isSelected())
        {
            iteration = 2; 
            initializeMaximum(); 
            mathTest = "SAT II Math Level 1"; 
            initializeEnemies();
        }
        
        else if(act.isSelected())
        {
            iteration = 3; 
            initializeMaximum(); 
            mathTest = "American College Test (ACT)"; 
            initializeEnemies();
        }
        
        else if(calcAB.isSelected())
        {
            iteration = 4; 
            initializeMaximum(); 
            mathTest = "AP Calculus AB"; 
            initializeEnemies();
        }
        
        else if(calcBC.isSelected())
        {
            iteration = 5; 
            initializeMaximum(); 
            mathTest = "AP Calculus BC"; 
            initializeEnemies();
        }
        
        else if(calcD.isSelected())
        {
            iteration = 6; 
            initializeMaximum(); 
            mathTest = "Multivariable Calculus (Calc D)"; 
            initializeEnemies();
        }
        
        status.repaint(); 
        ((MapPanel)map).updateText();
        ((BattlePanel)battlefield).updateText(); 
        ((ExaminationPanel)teacher).updateText();
    }

    public void initTI30()
    {
        calculator = "TI30X-IIA";
        calculatorAbility = "nPr nCr, Pick and Choose";
        calculatorAbilityDescription = "Deal up to 10 additional math.";
        atkBoost = 30; 
        ti30.setSelected(true);
    }

    public void initTI84()
    {
        calculator = "TI84-PLUS";
        calculatorAbility = "nDeriv";
        calculatorAbilityDescription = "Derivative +60 Math Points";
        atkBoost = 60;
        ti84.setSelected(true);
    }

    public void initTI89()
    {
        calculator = "TI89-TITANIUM";
        calculatorAbility = "fNINT";
        calculatorAbilityDescription = "Integral +60 Math Points";
        atkBoost = 60;
        ti89.setSelected(true);
    }
    public void initTIInspire()
    {
        calculator = "TI-Inspire";
        calculatorAbility = "Partial Fraction Decomposition";
        calculatorAbilityDescription = "Students/Teachers gain half of current understanding";
        atkBoost = 100;
        tiInspire.setSelected(true);
        if (pfd > 0)
        {
            ((ExaminationPanel)teacher).enablePFD();
            ((ExaminationPanel)teacher).disableIA();
        }
    }
    public void initWA()
    {
        calculator = "WolframAlpha";
        calculatorAbility = "Instant A+"; 
        calculatorAbilityDescription = "Students and teachers will gain full understanding";
        atkBoost = 150; 
        wa.setSelected(true); 
        if (apocalypse > 0)
        {
            ((ExaminationPanel)teacher).disablePFD();
            ((ExaminationPanel)teacher).enableIA();
        }
    }
    public void configureCalc(int power, int current)
    {
        switch (power)
        {
        case 5: wa.setEnabled(true);
        case 4: tiInspire.setEnabled(true);
        case 3: ti89.setEnabled(true);
        case 2: ti84.setEnabled(true);
        case 1: ti30.setEnabled(true);
        }
        
        switch(current)
        {
        case 1: initTI30(); break;
        case 2: initTI84(); break;
        case 3: initTI89(); break;
        case 4: initTIInspire(); break;
        case 5: initWA(); break; 
        }
    }
    
    public void configureTest()
    {
        switch (iteration)
        {
            case 6: calcD.setEnabled(true); 
            case 5: calcBC.setEnabled(true); 
            case 4: calcAB.setEnabled(true); 
            case 3: act.setEnabled(true);
            case 2: satII2.setEnabled(true); 
            case 1: satII1.setEnabled(true); 
            default: satI.setEnabled(true); 
        }
        
        switch(iteration)
        {
        case 0: mathTest = "SAT I Math"; satI.setSelected(true); break;
        case 1: mathTest = "SAT II Math Level 1"; satII1.setSelected(true); break;
        case 2: mathTest = "SAT II Math Level 2"; satII2.setSelected(true); break;
        case 3: mathTest = "American College Test (ACT)"; act.setSelected(true); break;
        case 4: mathTest = "AP Calculus AB"; calcAB.setSelected(true); break;
        case 5: mathTest = "AP Calculus BC"; calcBC.setSelected(true); break;
        default: mathTest = "Multivariable Calculus (Calc D)"; calcD.setSelected(true); break; 
        }
    }
    public void determineHeadphones()
    {
        int headphoneType = (int)(healPower * 2); 

        switch(headphoneType)
        {
        case 2: headphones = "FMJ by Skullcandy"; break;
        case 3: headphones = "Hesh by Paul Frank"; break;
        case 4: headphones = "G.I. by Skullcandy"; break;
        case 5: headphones = "Aviators by ROCNation"; break;
        case 6: headphones = "Harajuku Lovers by Monster"; break;
        case 7: headphones = "Heartbeats by Lady Gaga"; break; 
        case 8: headphones = "Beats by Dr. Dre"; break; 
        }
    }
    public void testScore()
    {
        if (score <= 0)
            score = 0; 
    }
}