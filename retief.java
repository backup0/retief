package UnNamedSource;

import java.awt.Color;
import java.awt.datatransfer.DataFlavor;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentEvent;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.util.Arrays;
import javax.swing.event.CaretEvent;
import javax.swing.event.DocumentEvent;
import javax.swing.event.PopupMenuEvent;
import javax.swing.text.BadLocationException;

public class RichTextEditor extends javax.swing.JTextPane 
{           

      public RichTextEditor (
              javax.swing.JScrollPane scrollPane, javax.swing.JFrame frame, javax.swing.JTextArea out)

      {
           this(scrollPane, frame);
           this.out = out; 
      }

      public RichTextEditor (javax.swing.JScrollPane scrollPane, javax.swing.JFrame frame)
      {
           super();
           reflectStyle = false;
           constructorInit(scrollPane, frame);
           _init_();
           this.addMouseListener(eventHandler);
           this.addMouseMotionListener(eventHandler);
           this.addCaretListener(eventHandler);
           this.addKeyListener(eventHandler);
           this.addComponentListener(eventHandler);
           this.addFocusListener(eventHandler);
           this.out = null;
      }      

      private void constructorInit(javax.swing.JScrollPane scrollPane, javax.swing.JFrame frame)
      {
          super.setMargin(new java.awt.Insets(0,0,0,0));
          super.setContentType(texthtml);

          xdoc = new javax.swing.text.html.HTMLDocument();

          super.setEnabled(false);

          eventHandler = new EventHandler();

          sdf = new java.text.SimpleDateFormat(EEEE, MMMM dd, yyyy);
          stf = new java.text.SimpleDateFormat(hmm a);

          buttonSet = false; 
          isFinalized = false; 

          sizes = null;
          defaultFont = null;
          defaultAlignment = null;
          defaultSize = null;
          urMgr = null;
          fntDlg = null;

          contentRich = null;
          contentPlain = null;

          getFonts();

          karet = super.getCaret();

          pageSet = false;
          sfd_ = false;

          biusQ = new boolean[4];
          fscQ = new String[3]; 
          subSuperQ = ; 

          cursor = new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR);

          theJSP = scrollPane;
          theFrame = frame;

          theJSP.setHorizontalScrollBarPolicy(javax.swing.JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
      }

      private void _init_()
      {

           isBold = false;
           isItalic = false;
           isUnderlined = false;
           isStriked = false;

           isSuper  = false;
           isSub = false;              

           isParaLeft = false;
           isParaRight = false;
           isParaCenter = false;
           isParaJustify = false;

           prevFont = ;
           prevSize = ;
           prevColor = ;
           currentColor = ;

           dispFont = ;
           dispSize = ;

           deleteCaretLoc = -666;
           insertCaretPos = -666;
           specInsertStart = -666;

           cleanDeleteEndOfPara = 0;
           deleteBlankPara = false;
           arrowDir = 0; 

           attySetChanged = false;
           globAttySet = null; 
           prevGlobAttySet = null;
           ppNextAS = null; 
           ppCurrentAS = null; 
           ppPrevAS = null;

           deleteString = ;
           deleteCode = 0;

           resetStyleQ();
           resetUndoRedo();

           _specT = false;
           searchRecord = null;
           addStuff = 0;
      }

      private void resetUndoRedo()
      {           
           BIUSplanInstance  = null;
           deleteURInstance = null;
           paraURInstance = null;
           replaceUndoRedo = null;
           indentUndoRedo = null;
           prevOp = 0;
           currentOp = null;
           bur = null;
           if (urMgr != null)
           {
                urMgr = new UndoRedo(urMgr.getSize());
           }
      }

      public void setToolbarButtons(
              javax.swing.JToggleButton b, 
              javax.swing.JToggleButton i, 
              javax.swing.JToggleButton u, 
              javax.swing.JToggleButton s, 
              javax.swing.JToggleButton sp, 
              javax.swing.JToggleButton sb, 
              javax.swing.JToggleButton pl, 
              javax.swing.JToggleButton pr, 
              javax.swing.JToggleButton pc, 
              javax.swing.JToggleButton pj, 
              javax.swing.JButton ii, 
              javax.swing.JButton di, 
              javax.swing.JButton color, 
              javax.swing.JComboBoxString f, 
              javax.swing.JComboBoxString sz, 
              javax.swing.JButton undo, 
              javax.swing.JButton redo,
              boolean showFontDialog 
               ) 
      {
           if (isFinalized) return;

           this.toolB = b;
           this.toolI = i;
           this.toolU = u;
           this.toolS = s;

           this.toolB.setActionCommand(BOLD);
           this.toolI.setActionCommand(ITALIC);
           this.toolU.setActionCommand(UNDERLINE);
           this.toolS.setActionCommand(STRIKETHROUGH);

           this.toolSuper = sp;
           this.toolSub = sb;

           this.toolSuper.setActionCommand(SUPERSCRIPT);
           this.toolSub.setActionCommand(SUBSCRIPT);

           this.toolC = color;
           this.toolF = f;
           this.toolSize = sz;

           this.toolC.setActionCommand(COLOR);
           this.toolF.setActionCommand(FONT);
           this.toolSize.setActionCommand(SIZE);

           this.toolParaL = pl;
           this.toolParaR = pr;
           this.toolParaC = pc;
           this.toolParaJ = pj;

           this.toolParaL.setActionCommand(PARA_LEFT);
           this.toolParaR.setActionCommand(PARA_RIGHT);
           this.toolParaC.setActionCommand(PARA_CENTER);
           this.toolParaJ.setActionCommand(PARA_JUSTIFY);

           this.toolIncIndent = ii; 
           this.toolDecIndent = di;

           this.toolIncIndent.setActionCommand(increase indent);
           this.toolDecIndent.setActionCommand(decrease indent);

           this.toolUndo = undo;
           this.toolRedo = redo;

           this.toolUndo.setActionCommand(undo);
           this.toolRedo.setActionCommand(redo);

           this.toolUndo.setEnabled(false);
           this.toolRedo.setEnabled(false);

           this.toolUndo.addActionListener(eventHandler);
           this.toolRedo.addActionListener(eventHandler);
           this.toolB.addActionListener(eventHandler);          
           this.toolI.addActionListener(eventHandler);          
           this.toolU.addActionListener(eventHandler);          
           this.toolS.addActionListener(eventHandler);
           this.toolC.addActionListener(eventHandler);
           this.toolSuper.addActionListener(eventHandler);
           this.toolSub.addActionListener(eventHandler); 
           this.toolParaL.addActionListener(eventHandler);
           this.toolParaR.addActionListener(eventHandler);
           this.toolParaC.addActionListener(eventHandler);          
           this.toolParaJ.addActionListener(eventHandler);
           this.toolIncIndent.addActionListener(eventHandler);
           this.toolDecIndent.addActionListener(eventHandler);
           this.toolF.addActionListener(eventHandler);
           this.toolSize.addActionListener(eventHandler);

           populateFontDropMenu();

           sfd_ = showFontDialog;
           buttonSet = true;
      }

      public final void setSizeRange(int[] size)
      {
           if (isFinalized) return;
           sizes = size;
      }

      public final void setUndoRedoStep(int urstep)
      {
           if (isFinalized) return;
           if (urstep  10) urstep = 10;
           urMgr = new UndoRedo(urstep);
      }

      private boolean sfd_;

      public final void makeFinal()
      {

           if (urMgr == null)
           {
                setUndoRedoStep(0);
           }

           if (sizes == null)
           {
                sizes = new int[25];
                int i = 0;
                while (i  sizes.length)
                {
                     sizes[i] = i + 8;
                     i++;
                }    
           }

           _specT = true;
           if (buttonSet) 
           {
                int i = 0;

                while (i  sizes.length)
                {
                     toolSize.addItem(String.valueOf(sizes[i]));
                     i++;
                }
           }

           if (!buttonSet  sfd_)
           {
                fntDlg = new FontDialog(fonts);
           }
           _specT = false;

           if (!pageSet)
           {
                setContentBlank();
           }

           isFinalized = true;
           setEnabled(true);
           reflectStyle = true;
           sizes = null;
           fonts = null;
           setCaretPosition(getStartLoc());
           requestFocusInWindow();
      }

      private void buttonExecAction(java.awt.event.ActionEvent obj)
      {
           String cmd = obj.getActionCommand();

           if (cmd.equalsIgnoreCase(undo))
           {
                undo();
                return;
           }
           if (cmd.equalsIgnoreCase(redo))
           {
                redo();
                return;
           }
           if (cmd.equalsIgnoreCase(BOLD))
           {
                setBold();
                return;
           }
           if (cmd.equalsIgnoreCase(ITALIC))
           {
                setItalic();
                return;
           }
           if (cmd.equalsIgnoreCase(UNDERLINE))
           {
                setUnderline();
                return;
           }
           if (cmd.equalsIgnoreCase(STRIKETHROUGH))
           {
                setStrikeThrough();
                return;
           }
           if (cmd.equalsIgnoreCase(PARA_LEFT))
           {
                setParaLeft();
                return;
           }
           if (cmd.equalsIgnoreCase(PARA_RIGHT))
           {
                setParaRight();
                return;
           }
           if (cmd.equalsIgnoreCase(PARA_CENTER))
           {
                setParaCenter();
                return;
           }
           if (cmd.equalsIgnoreCase(PARA_JUSTIFY))
           {
                setParaJustify();
                return;
           }
           if (cmd.equalsIgnoreCase(decrease indent))
           {
                decreaseIndent();
                return;
           }
           if (cmd.equalsIgnoreCase(increase indent))
           {
                increaseIndent();
                return;
           }
           if (cmd.equalsIgnoreCase(FONT))
           {
                fontComboClicked();
                return;
           }
           if (cmd.equalsIgnoreCase(dlg + FONT))
           {
                dialogOpened = true;
                fntDlg.show
                    (new boolean[]{isBold, isItalic, isUnderlined, isStriked}, 
                            dispFont, dispSize, txtGetX(), txtGetY());
                return;
           }
           if (cmd.equalsIgnoreCase(SIZE))
           {
                sizeComboClicked();
                return;
           }
           if (cmd.equalsIgnoreCase(COLOR))
           {
                setColor();
                return;
           }
           if (cmd.equalsIgnoreCase(SUPERSCRIPT))
           {
                setSuperscript();
                return;
           }
           if (cmd.equalsIgnoreCase(SUBSCRIPT))
           {
                setSubscript();
                return;
           }
           if (cmd.equalsIgnoreCase(kopy))
           {
                copy();
                return;
           }
           if (cmd.equalsIgnoreCase(kut))
           {
                cut();
                return;
           }
           if (cmd.equalsIgnoreCase(paste))
           {
                paste();
                return;
           }
           if (cmd.equalsIgnoreCase(search))
           {
                dialogOpened = true;
                iSearch();
                return;
           }
           if (cmd.equalsIgnoreCase(replace))
           {
                dialogOpened = true;
                iReplace();
                return;
           }
           if (cmd.equalsIgnoreCase(specar))
           {
                dialogOpened = true;
                insertSpec();
                return;
           }
           if (cmd.equalsIgnoreCase(wcount))
           {
                dialogOpened = true;
                countWord(true);
           }
      }

      @Override

      public void setEnabled(boolean e)
      {
           if (isFinalized)
           {
               super.setEnabled(e);
           }
      }

      private int[] addDefaultSize(int[] sr, int ds)
      {
           boolean y;
           int i = 0;

           y = false;
           while (i  sr.length)
           {
                if (sr[i] == ds)
                {
                     y = true;
                     break;
                }
                i++;
           }

           if (!y)
           {
                sr = Arrays.copyOf(sr, sr.length + 1);
                sr[sr.length - 1] = ds;
           }
           return sr;
      }

      private int[] getSizeRange(javax.swing.JComboBox cs)
      {
           int i;
           i = cs.getItemCount();
           int[] sr = new int[i];
           while (i  0)
           {
               --i;
               try
               {                    
                    sr[i] = Integer.parseInt(cs.getItemAt(i).toString());
               }
               catch (java.lang.NumberFormatException n)
               {
                    sr[i] = -1;
               }
           }
           return sr;
      }

      private void addStuffToSizeBox(javax.swing.JComboBoxString jcb, int[] s)
      {
           int i = 0;

           _specT = true; 

           jcb.removeAllItems();
           while (i  s.length)
           {
                if (s[i] = 0)
                {
                     jcb.addItem(String.valueOf(s[i]));
                }
                i++;
           }
           _specT = false;
      }

      private void repopulateSizeBox(int ds)
      {
           int sr[];

           if (buttonSet)
           {
                sr = getSizeRange(toolSize);
           }
           else
           {
                sr = getSizeRange(fntDlg.getSizeCombo());
           }

           sr = addDefaultSize(sr, ds);

           Arrays.sort(sr);

           if (buttonSet)
           {
                addStuffToSizeBox(toolSize, sr);
           }

           if (fntDlg != null)
           {
                addStuffToSizeBox(fntDlg.getSizeCombo(), sr);
           }
      }

      private void preparePage()
      {
           if (searchDlg != null)
           {
                searchDlg.dispose();
                searchDlg = null;
           }

           if (searchReplaceDlg != null)
           {
                searchReplaceDlg.dispose();
                searchReplaceDlg = null;
           }

           
      public void setContentBlank()
      {
           setDefaultFSC();
           preparePage();
      }

      public void setContentBlank(String font, String size, String alignment)
      {
           setDefaultFSC(font, size, alignment);
           preparePage();
      }

      public void setContentPlain(String content)
      {
           contentPlain = content;
           setDefaultFSC();
           preparePage();
      }

      public void setContentPlain(String content, String font, String size, String alignment)
      {
           contentPlain = content;
           setDefaultFSC(font, size, alignment);
           preparePage();
      }

      public void setContentRich(String content)
      {
           contentRich = content;           
           preparePage();
      }

      private int getEndLoc()
      {
           return doc.getLength();
      }

      private int getStartLoc()
      {
           javax.swing.text.Element pe = doc.getParagraphElement(0);

           if (isP_Implied(pe.getAttributes()))
           {
                pe = doc.getParagraphElement(pe.getEndOffset());
                return pe.getStartOffset();
           }
           else
           {
                return 0;
           }
      }

      private void getFonts()
      {
           fonts = java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
      }

      private void populateFontDropMenu ()
      {
           int i = 0;

           if (fonts == null)
           {
                getFonts();
           }

           _specT = true;
           while (i  fonts.length)
           {
                toolF.addItem(fonts[i]);
                i++;
           }
           _specT = false;
      }

      private javax.swing.text.Element getNonBR(javax.swing.text.Element em)
      {
           javax.swing.text.Element pe = doc.getParagraphElement(em.getStartOffset());

           if ((isFirstPara(pe) && em.getStartOffset() == pe.getStartOffset()) 
                   (pe.getStartOffset() = 0 && em.getStartOffset() == pe.getStartOffset()))
           {
                return em;
           }

           while(true)
           {
                if (isBR(em.getAttributes())  (em.getAttributes().getAttribute(CR) != null))

                {
                     em = doc.getCharacterElement(em.getStartOffset() - 1);
                     pe = doc.getParagraphElement(em.getStartOffset());
                     if ((isFirstPara(pe) && em.getStartOffset() == pe.getStartOffset()) 
                             (pe.getStartOffset() = 0 && em.getStartOffset() == pe.getStartOffset()))
                     {

                          break;
                     }
                }
                else
                {
                     break;
                }
           }
           return em;
      }

      private javax.swing.text.Element getNonBRfw(javax.swing.text.Element em)
      {
           javax.swing.text.Element orig = em;

           do
           {

                if (em.getAttributes().getAttribute(CR) != null)
                {
                     em = orig;
                     break;
                }
                else
                {
                     if(!isBR(em.getAttributes()))
                     {
                          break;
                     }
                     em = doc.getCharacterElement(em.getEndOffset());
                }
           } while (true);
           return em;
      }

      private boolean outOfRange(int start, int end)
      {
           return start  end  
                   start  getStartLoc()  start  getEndLoc()  
                   end  getStartLoc()  end  getEndLoc();
      }

      private void insertText(String t, javax.swing.text.AttributeSet as)
      {
           if (outOfRange(getCaretPosition(), getCaretPosition()))
           {
                resetUndoRedo();
                this.setCaretPosition(getStartLoc());
                return;
           }

           int i = 0;
           try
           {
                procTextEntry(t.length());
                i++;
                insertText(t, this.getSelectionStart(), as);
           }
           catch (javax.swing.text.BadLocationException exkeption) 
           {
                if (i == 0)
                {
                     resetUndoRedo();
                }
                else
                {
                     insertCaretPos = insertCaretPos - t.length();
                     recOp(CARET_MOVE, prevOp);
                }
           }
      }

      private void insertText (String t, int caretLoc, javax.swing.text.AttributeSet as)
              throws javax.swing.text.BadLocationException
      {
           javax.swing.text.Element em, emx, pe = doc.getParagraphElement(caretLoc);
           em = emx = doc.getCharacterElement(caretLoc);

           if (as != null)
           {
                doc.insertString(caretLoc, t, as);
           }
           else
           {
                if (isBlockEditable(em))
                {    

                     if (em.getStartOffset() == caretLoc) 

                     {

                          if (pe.getStartOffset() == em.getStartOffset())
                          {

                               doc.insertString(caretLoc, t, em.getAttributes());
                          }
                          else
                          {
                               em = doc.getCharacterElement(em.getStartOffset() - 1);
                               em = getNonBR(em);
                               if (isBlockEditable(em))
                               {
                                    doc.insertString(caretLoc, t, em.getAttributes());
                               }
                               else
                               {
                                    doc.insertString(caretLoc, t, null);
                               }
                               em = emx;
                          }
                     }
                     else
                     {

                          doc.insertString(caretLoc, t, em.getAttributes());

                     }
                }
                else 
                {
                     if (as != null)
                     {
                         doc.insertString(caretLoc, t, as);
                     }
                     else
                     {

                          if (isFirstPara(pe) && (pe.getStartOffset() == em.getStartOffset()))
                          {
                                doc.insertString(caretLoc, t, null);
                          }
                          else
                          {
                                em = doc.getCharacterElement(em.getStartOffset() - 1);

                                em = getNonBR(em);
                                if (isFirstPara(doc.getParagraphElement(em.getStartOffset())))
                                {
                                     if (isBlockEditable(em))
                                     {
                                          doc.insertString(caretLoc, t, em.getAttributes());
                                     }
                                     else
                                     {
                                          doc.insertString(caretLoc, t, null);
                                     }
                                }
                                else
                                {
                                     if (isBlockEditable(em))
                                     {
                                          doc.insertString(caretLoc, t, em.getAttributes());
                                     }
                                     else
                                     {
                                          doc.insertString(caretLoc, t, null);
                                     }
                                }
                                em = emx;
                          }
                     }
                }
           }
           splitMod(em, style= + getCurrentStyle(em) +, this.getSelectionEnd());
           this.repaint();
      }

      private boolean noSelection()
      {

           return getSelectionStart() == getSelectionEnd();
      }

      public void setSuperscript()
      {
           if(noSelection())
           {
                styleQLoc = this.getCaretPosition();
                if (isSuper)
                {
                     subSuperQ = ;
                     isSub = false;
                     isSuper = false;
                }
                else
                {
                     subSuperQ = SUPERSCRIPT;
                     isSub = false;
                     isSuper = true;
                }
                resetSubSuperButtons();
                return;
           }

           if (isSuper) 
           {
                BIUSplanInstance = BIUSplan(SET_NO_SUPER);
                recOp(SET_NO_SUB, prevOp);
                setStyle(new String[] {VERTICAL_ALIGN, });
                isSub = false;
                isSuper = false;
           }
           else
           {
                BIUSplanInstance = BIUSplan(SET_SUPER, SUPERSCRIPT);
                recOp(SET_SUB, prevOp);
                setStyle(new String[] {VERTICAL_ALIGN, SUPERSCRIPT});
                isSub = false;
                isSuper = true;
           }
           prevOp = CARET_MOVE;
           resetSubSuperButtons();

      }

      public void setSubscript()
      {    
           if(noSelection())
           {
                styleQLoc = this.getCaretPosition();
                if (isSub)
                {
                     subSuperQ = ;
                     isSub = false;
                     isSuper = false;
                }
                else
                {
                     subSuperQ = SUBSCRIPT;
                     isSub = true;
                     isSuper = false;
                }
                resetSubSuperButtons();
                return;
           }

           if (isSub) 
           {
                BIUSplanInstance = BIUSplan(SET_NO_SUB);
                recOp(SET_NO_SUB, prevOp);
                setStyle(new String[] {VERTICAL_ALIGN, });
                isSub = false;
                isSuper = false;
           }
           else
           {
                BIUSplanInstance = BIUSplan(SET_SUB, SUBSCRIPT);
                recOp(SET_SUB, prevOp);
                setStyle(new String[] {VERTICAL_ALIGN, SUBSCRIPT});
                isSub = true;
                isSuper = false;
           }
           prevOp = CARET_MOVE;
           resetSubSuperButtons();
      }

      public void setBold()
      {
          if(noSelection())
          {
               styleQLoc = this.getCaretPosition();
               biusQ[0] = !isBold;
               biusQ[1] = isItalic;
               biusQ[2] = isUnderlined;
               biusQ[3] = isStriked;
               isBold = biusQ[0];
               resetBIUSbuttons();
               return;
          }

          if (isBold)
          {
               BIUSplanInstance = BIUSplan(SET_TEXT_STYLE_NO_B);
               recOp(SET_TEXT_STYLE_NO_B, prevOp);
               setStyle(new String[] {NO_BOLD});
               isBold = false;
          }
          else
          {
               BIUSplanInstance = BIUSplan(SET_TEXT_STYLE_B);
               recOp(SET_TEXT_STYLE_B, prevOp);
               setStyle(new String[] {BOLD});
               isBold = true;
          }
          resetBIUSbuttons();
      }

      public void setItalic()
      {
          if(noSelection())
          {
               styleQLoc = this.getCaretPosition();
               biusQ[0] = isBold;
               biusQ[1] = !isItalic;
               biusQ[2] = isUnderlined;
               biusQ[3] = isStriked;
               isItalic = biusQ[1];
               resetBIUSbuttons();
               return;
          }

          if (isItalic)
          {
               BIUSplanInstance = BIUSplan(SET_TEXT_STYLE_NO_I);
               recOp(SET_TEXT_STYLE_NO_I, prevOp);
               setStyle(new String[] {NO_ITALIC});
               isItalic = false;
          }  
          else
          {
               BIUSplanInstance = BIUSplan(SET_TEXT_STYLE_I);
               recOp(SET_TEXT_STYLE_I, prevOp);
               setStyle(new String[] {ITALIC});
               isItalic = true;
          }
          resetBIUSbuttons();
      }

      public void setUnderline()
      {
          if(noSelection())
          {
               styleQLoc = this.getCaretPosition();
               biusQ[0] = isBold;
               biusQ[1] = isItalic;
               biusQ[2] = !isUnderlined;
               biusQ[3] = isStriked;
               isUnderlined = biusQ[2];
               resetBIUSbuttons();
               return;
          }

          if (isUnderlined)
          {
               BIUSplanInstance = BIUSplan(SET_TEXT_STYLE_NO_U);
               recOp(SET_TEXT_STYLE_NO_U, prevOp);
               setStyle(new String[] {NO_UNDERLINE});
               isUnderlined = false;
          }
          else
          {
               BIUSplanInstance = BIUSplan(SET_TEXT_STYLE_U);
               recOp(SET_TEXT_STYLE_U, prevOp);
               setStyle(new String[] {UNDERLINE});
               isUnderlined = true;
          }
          resetBIUSbuttons();
      }

      public void setStrikeThrough()
      {

          if(noSelection())
          {
               styleQLoc = this.getCaretPosition();
               biusQ[0] = isBold;
               biusQ[1] = isItalic;
               biusQ[2] = isUnderlined;
               biusQ[3] = !isStriked;
               isStriked = biusQ[3];
               resetBIUSbuttons();
               return;
          }

          if (isStriked)
          {
               BIUSplanInstance = BIUSplan(SET_TEXT_STYLE_NO_S);
               recOp(SET_TEXT_STYLE_NO_S, prevOp);
               setStyle(new String[] {NO_STRIKETHROUGH});
               isStriked = false;
          }
          else
          {
               BIUSplanInstance = BIUSplan(SET_TEXT_STYLE_S);
               recOp(SET_TEXT_STYLE_S, prevOp);
               setStyle(new String[] {STRIKETHROUGH});
               isStriked = true;
          }
          resetBIUSbuttons();
      }

      public void setColor()
      {

           java.awt.Color color = javax.swing.JColorChooser.showDialog(this, Color..., null);

           if (color == null)
           {
                return;
           }

           String c;
           String red = Integer.toHexString(color.getRed());
           String green = Integer.toHexString(color.getGreen());
           String blue = Integer.toHexString(color.getBlue());
           if (red.length() == 1)
           {
                red = 0 + red;
           }
           if (green.length() == 1)
           {
                green = 0 + green;
           }
           if (blue.length() == 1)
           {
                blue = 0 + blue;
           }

           c = # + red + green + blue;

           if (noSelection())
           {
                styleQLoc = this.getCaretPosition();
                biusQ[0] = isBold;
                biusQ[1] = isItalic;
                biusQ[2] = isUnderlined;
                biusQ[3] = isStriked;
                fscQ[2] = c;
                resetFSCdisplay();
                return;
           }

           BIUSplanInstance = BIUSplan(SET_TEXT_COLOR, c);
           if (this.prevOp == SET_TEXT_COLOR)
           {
               this.prevOp = CARET_MOVE;
           }
           recOp(SET_TEXT_COLOR, prevOp);
           setStyle(new String[] {COLOR, c});
      }

      public void setFont(String f)
      {
           if (_specT) return;

           if (noSelection())
           {
                styleQLoc = this.getCaretPosition();
                biusQ[0] = isBold;
                biusQ[1] = isItalic;
                biusQ[2] = isUnderlined;
                biusQ[3] = isStriked;
                fscQ[0] = f;
                return;
           }

           BIUSplanInstance = BIUSplan(SET_FONT, f);
           if (this.prevOp == SET_FONT)
           {
               this.prevOp = CARET_MOVE;
           }
           recOp(SET_FONT, prevOp);
           setStyle(new String[] {FONT, f});
      }

      private boolean formatParaOk(String s)
      {
           if (outOfRange(getSelectionStart(), getSelectionEnd() ))
           {
                return false;
           }

           javax.swing.text.Element pe = doc.getParagraphElement(getSelectionStart());

           if (getSelectionStart() = pe.getStartOffset() && getSelectionEnd() = pe.getEndOffset())
           {
                return !getParaStyle(pe.getAttributes()).equals(s);
           }
           else
           {
                return true;
           }
      }

      public void increaseIndent()
      {
           setParagraphIndent((byte) 2, true);
      }

      public void decreaseIndent()
      {
           setParagraphIndent((byte) 0, true);
      }

      public void setParaLeft()
      {
           if (formatParaOk(PARA_LEFT))
           {
                formatParagraph(PARA_LEFT);
           }
           else
           {
                resetParaButtons();
           }
      }

      public void setParaRight()
      {
           if (formatParaOk(PARA_RIGHT))
           {
                formatParagraph(PARA_RIGHT);
           }
           else
           {
                resetParaButtons();
           }
      }

      public void setParaCenter()
      {
           if (formatParaOk(PARA_CENTER))
           {
                formatParagraph(PARA_CENTER);
           }
           else
           {
                resetParaButtons();
           }
      }

      public void setParaJustify()
      {
           if (formatParaOk(PARA_JUSTIFY))
           {
                formatParagraph(PARA_JUSTIFY);
           }
           else
           {
                resetParaButtons();
           }
      }

      boolean _specT; 

      public void setSize (String s)
      {
           if (_specT) return;

           if (noSelection())
           {
                styleQLoc = this.getCaretPosition();
                biusQ[0] = isBold;
                biusQ[1] = isItalic;
                biusQ[2] = isUnderlined;
                biusQ[3] = isStriked;
                fscQ[1] = s;
                return;
           }

           BIUSplanInstance = BIUSplan(SET_SIZE, s);

           if (this.prevOp == SET_SIZE)
           {
               this.prevOp = CARET_MOVE;
           }

           recOp(SET_SIZE, prevOp);
           setStyle(new String[] {SIZE, s});
      }

      private void setStyle(String[] mod, int start, int end)
      {    

           int lte = getEndLoc() - getStartLoc();
           doSetStyle(mod, start, end);
           detach();

           if (lte != (getEndLoc() - getStartLoc()))
           {
                resetUndoRedo();
           }
      }

      private void setStyle(String[] mod)
      {

          int selStart = getSelectionStart(), selEnd = getSelectionEnd();

          if (outOfRange(selStart, selEnd))
          {
               resetUndoRedo();
               return;
          }

          boolean gfs = forwardSelection();
          setStyle(mod, selStart, selEnd);
          resetSelection(selStart, selEnd, gfs);
          resetStyleQ();
      }

      private void extSetStyle(String[] mod, int selStart, int selEnd)
      {

          doSetStyle(mod, selStart, selEnd);

      }

      private void doSetStyle(String[] mod, int selStart, int selEnd)  
      {
           reflectStyle = false;

           String oldStyle, newStyle;
           int i = 0;
           javax.swing.text.Element block = doc.getCharacterElement(selStart);

           while (true)
           {
               if (i  doc.getLength()) break;
               oldStyle = style= + getCurrentStyle(block) + ;
               newStyle = style= + modifyCurrentStyle(block, mod) + ;

               if (selStart  block.getStartOffset() && selEnd  block.getEndOffset()) 
               {

                    block = splitMid(block, oldStyle, newStyle, selStart, selEnd);
               }
               else if (selStart = block.getStartOffset() && selEnd = block.getEndOffset()) 
               {

                    block = splitMod(block, newStyle, selEnd);
               }
               else if (selStart = block.getStartOffset() && selEnd  block.getEndOffset()) 
               {

                    block = splitStart(block, oldStyle, newStyle, selEnd);
               }
               else if (selStart  block.getStartOffset() && selEnd = block.getEndOffset()) 
               {

                    block = splitEnd(block, oldStyle, newStyle, selStart, selEnd);
               }
               else
               {

                    block = null;
               }

               if (block == null) break;

               i++;
           }
           reflectStyle = true; 
      }

      private boolean forwardSelection()
      {
           return karet.getDot() = karet.getMark();
      }

      private void resetSelection (int start, int end, boolean b)
      {    
           javax.swing.text.Element em, pe;

           em = doc.getCharacterElement(end - 1);
           pe = doc.getParagraphElement(end);

           if (pe.getEndOffset() - 1 == end)
           {
                end = pe.getEndOffset() - 1;
                if (isBR(em.getAttributes()))
                {
                     end = end - 1;
                }
           }

           if (start  end)
           {
                start = end;
                if (outOfRange(start, end))
                {
                     start = end = getStartLoc();
                }                    
           }

           if (b)
           {
               karet.setDot(start);
               karet.moveDot(end);
           }
           else
           {
               karet.setDot(end);
               karet.moveDot(start);
           }
           reflectStyle();
      }

      private void exFormatParagraph(javax.swing.text.Element em, String style)
              throws javax.swing.text.BadLocationException, java.io.IOException
      {

         String pStartTag = p style= + style + ;
         String content = getContBlocks(em, em.getStartOffset(), em.getEndOffset());
         doc.setOuterHTML(em, pStartTag + content + p);
      }

      private void formatParagraph(String style)
      {                      
           try
           {
                formatParagraph(style, this.getSelectionStart(), this.getSelectionEnd(), true);
           }
           catch (BadLocationException  java.io.IOException ee)
           {
                detach();
                this.setCaretPosition(getStartLoc());
                resetUndoRedo();
           }
      }

      private void formatParagraph(String style, int startPos, int endPos, boolean ur)
              throws BadLocationException, java.io.IOException
      {

          if (ur && (!canSetParaAlignment(style, startPos, endPos)))
          {
               recOp(CARET_MOVE, prevOp);
               return;
          }

          reflectStyle = false;
          int i = startPos;
          boolean f = forwardSelection();
          String modStyle;
          paraURInstance = new ParaUndoRedo(style, startPos, endPos); 
          javax.swing.text.Element em;
          Object fart;

          if (ur) 
          {
              if (this.prevOp == SET_PARA_STYLE_L)
              {
                    this.prevOp = CARET_MOVE;
              }
              recOp(SET_PARA_STYLE_L, prevOp);
          }

          int[] startEnd = new int[]{0, 0};
          do
          {
                em = doc.getParagraphElement(i);
                if (em.getStartOffset() == startEnd[0] && em.getEndOffset() == startEnd[1])
                {
                     break;
                }
                startEnd[0] = em.getStartOffset(); startEnd[1] = em.getEndOffset();
                i = em.getEndOffset();
                fart = em.getAttributes().getAttribute(javax.swing.text.html.CSS.Attribute.MARGIN_LEFT);
                paraURInstance.addParaBlock(getParaStyle(em.getAttributes()), em.getStartOffset());
                modStyle = text-align + style + ;;
                if (fart!= null)
                {
                     modStyle = modStyle + margin-left + fart + ;;
                }
                exFormatParagraph(em, modStyle);

          } while (endPos = em.getEndOffset()); 

          if (ur)
          {
               detach();
               reflectStyle = true;
               resetSelection(startPos, endPos, f);    
          }
      }

      private boolean canSetParaAlignment(String style, int startPos, int endPos)
      {
           if (outOfRange(startPos, endPos))
           {
                return false;
           }

           int i = startPos;
           boolean rslt = false;
           javax.swing.text.Element pe;
           do
           {
                pe = doc.getParagraphElement(i);
                i = pe.getEndOffset();
                if (getParaStyle(pe.getAttributes()).compareTo(style) != 0)
                {
                     rslt = true;
                     break;
                }
           } while(endPos  pe.getEndOffset());
           return rslt;
      }

      private void setParagraphIndent(byte pos, boolean ur)
      {   
           try
           {
                setParagraphIndent(pos, getSelectionStart(), getSelectionEnd(), ur);

           }
           catch (BadLocationException  java.io.IOException ee)
           {
                detach();
                this.setCaretPosition(getStartLoc());
                resetUndoRedo();
           }
      }

      private boolean canChangeIndent(byte pos, int startPos, int endPos)
      {
           if (outOfRange(startPos, endPos))
           {
                return false;
           }

           if (pos != 0) return true;
           boolean yes = false;
           javax.swing.text.Element pe = doc.getParagraphElement(startPos);
           Object o;
           while (endPos  pe.getEndOffset())
           {
                o = pe.getAttributes().getAttribute(javax.swing.text.html.CSS.Attribute.MARGIN_LEFT);
                if (o != null)
                {
                     yes = true;
                     break;
                }

                pe = doc.getParagraphElement(pe.getEndOffset());
           }

           if (endPos = pe.getEndOffset() && !yes)
           {
                o = pe.getAttributes().getAttribute(javax.swing.text.html.CSS.Attribute.MARGIN_LEFT);
                if (o != null) return true;
           }
           return yes;
      }

      private void setParagraphIndent(byte pos, int startPos, int endPos, boolean ur)
              throws BadLocationException, java.io.IOException
      {       

          if (ur && (!canChangeIndent(pos, startPos, endPos)))
          {
               recOp(CARET_MOVE, prevOp);
               return;

          }

          reflectStyle = false;
          int i = startPos;
          boolean f = forwardSelection();
          String modStyle, style;
          javax.swing.text.Element em;

          int[] startEnd = new int[]{0, 0};
          do

          {
                em = doc.getParagraphElement(i);
                i = em.getEndOffset();
                if (em.getStartOffset() == startEnd[0] && em.getEndOffset() == startEnd[1])
                {
                     break;
                }
                startEnd[0] = em.getStartOffset(); startEnd[1] = em.getEndOffset();

                style = getParaStyle(em.getAttributes());

                modStyle = text-align + style + ;;

                style = getNewIndent(em, pos);

                if (style.length()  0)
                {
                     modStyle = margin-left + style + ; + modStyle;
                }
                exFormatParagraph(em, modStyle);

          } while (endPos = em.getEndOffset());

          if (ur) 
          {
               indentUndoRedo = new ParaIndentUndoRedo(startPos, endPos, pos);

               if (this.prevOp == SET_PARA_INDENT)
               {
                    this.prevOp = CARET_MOVE;
               }
               recOp(SET_PARA_INDENT, prevOp);
          } 
          detach();
          reflectStyle = true;
          resetSelection(startPos, endPos, f);
      }

      private String getNewIndent(javax.swing.text.Element pe, byte i)
      {
           Object o = pe.getAttributes().getAttribute(javax.swing.text.html.CSS.Attribute.MARGIN_LEFT);
           String s;
           int currentIndent = 0;

           if (o != null)
           {
                s = o.toString();
                s = s.replace(px, );
                currentIndent = Integer.parseInt(s);
           }

           if (i == 0) 
           {
                if (currentIndent  0)
                {
                     currentIndent = currentIndent - INDENT_SIZE;
                }

                if (currentIndent  0)
                {
                     currentIndent = 0;
                }
           }
           else 
           {
                currentIndent = currentIndent + INDENT_SIZE;
           }

           if (currentIndent == 0)
           {
                return ;
           }
           else
           {
                return currentIndent + px;
           }
      }

      private String getParagraphIndent(javax.swing.text.Element pe)
      {
           return getParagraphIndent(pe.getAttributes());
      }

      private String getParagraphIndent(javax.swing.text.AttributeSet as)
      {
           Object o = as.getAttribute(javax.swing.text.html.CSS.Attribute.MARGIN_LEFT);
           if (o != null)
           {
                return o.toString();
           }
           else
           {
                return ;
           }
      }   

      private boolean isBR(javax.swing.text.AttributeSet as)
      {
           if (as == null) return false;
           return (as.containsAttribute(javax.swing.text.StyleConstants.NameAttribute,javax.swing.text.html.HTML.Tag.BR));
      }

      private boolean isPara(javax.swing.text.AttributeSet as)
      {
           if (as == null) return false;
           return as.containsAttribute(CR, true);
      }

      private boolean isBlockEditable(javax.swing.text.Element block)
      {
           javax.swing.text.AttributeSet as = block.getAttributes();

           if(as.getAttribute(CR) != null)
           {
               return false;
           }
           return !isBR(as);
      }

      private void queueForQuickFormat(QuickFormatScheme qf, int caretPos)
      {
           if (quickFormat == null)
           {   
                resetStyleQ();
                return;
           }

           resetStyleQ();
           styleQLoc = caretPos;
           biusQ[0] = qf.b;
           biusQ[1] = qf.i;
           biusQ[2] = qf.u;
           biusQ[3] = qf.s;
           isBold = biusQ[0];
           isItalic = biusQ[1];
           isUnderlined = biusQ[2];
           isStriked = biusQ[3];

           if (qf.font != null)
           {
               fscQ[0] = qf.font;
           }
           if (qf.size != null)
           {
               fscQ[1] = qf.size;
           }
           if (qf.color != null)
           {
               fscQ[2] = qf.color;
           }

          resetFSCdisplay();
          resetBIUSbuttons();
          resetSubSuper();
      }

      public void undo ()
      {
          resetStyleQ();
          if (!urMgr.undoable) return;

          reflectStyle = false;

          int s = getSelectionStart(), e = getSelectionEnd();
          boolean f = forwardSelection();
          int[] fax;
          try
          {          
               recOp(UNDO, prevOp);
               fax = modifySelection(urMgr.undo(), s, e, (byte) 0);
          }
          catch (javax.swing.text.BadLocationException  java.io.IOException ekzeptions)
          {

               fax = new int[]{getStartLoc(), getStartLoc()};
               resetUndoRedo();
          }       
          detach();
          reflectStyle = true;
          resetSelection(fax[0], fax[1], f);
      }

      public void redo ()
      {
          resetStyleQ();
          if (!urMgr.redoable) return;

          reflectStyle = false;
          int s = getSelectionStart(), e = getSelectionEnd();
          boolean f = forwardSelection();
          int[] fax;
          try
          {
               recOp(REDO, prevOp);
               fax = modifySelection(urMgr.redo(), s, e, (byte) 1);
          }
          catch (javax.swing.text.BadLocationException  java.io.IOException ekzeptions)
          {

               fax = new int[]{getStartLoc(), getStartLoc()};
               resetUndoRedo();
          }
          detach();
          reflectStyle = true;
          resetSelection(fax[0], fax[1], f);
      }

      private int[] modifySelection(UndoRedoObject urObj, int start, int end, byte ur)
      {
           int[] newSel = new int[]{start, end};
           switch (urObj.getType())
           {
                case UndoRedoObject.DELETE 
                case UndoRedoObject.DELETE_SELECTION
                     if (ur == 0)
                     {
                          newSel[0] = urObj.getEnd();
                          newSel[1] = urObj.getEnd();
                     }
                     else
                     {
                          newSel[0] = urObj.getStart();
                          newSel[1] = urObj.getStart();
                     }
                     break;
                case UndoRedoObject.INSERT 
                     if (ur == 0)
                     {                          
                          newSel[0] = urObj.getStart();
                          newSel[1] = urObj.getStart();
                     }
                     else
                     {
                          newSel[0] = urObj.getEnd();
                          newSel[1] = urObj.getEnd();
                     }
                     break;
                case UndoRedoObject.REPLACE
                     newSel[0] = urObj.getStart();
                     newSel[1] = urObj.getStart();
                     break;
                case UndoRedoObject.QUICK_FORMAT
                case UndoRedoObject.STYLE
                     newSel[0] = urObj.getStart();
                     newSel[1] = urObj.getEnd();
                     break;                            
           }
           return newSel;
      }

      private void addNewLine (int selStart) throws 
              javax.swing.text.BadLocationException, java.io.IOException, java.lang.IllegalArgumentException
      {

           if (outOfRange(selStart, selStart))
           {
                resetUndoRedo();
                this.setCaretPosition(getStartLoc());
                return;
           }

           javax.swing.text.Element em = doc.getCharacterElement(selStart);
           javax.swing.text.Element pe = doc.getParagraphElement(em.getStartOffset());
           int i;

           if (selStart == pe.getStartOffset())
           {                                
                if (pe.getEndOffset() - pe.getStartOffset() == 1)
                {
                     doc.insertAfterStart(pe, brbr);
                }
                else
                {
                     doc.insertAfterStart(pe, br);
                }
                i = (doc.getCharacterElement(pe.getStartOffset())).getEndOffset();
           }

           else if (selStart == (pe.getEndOffset() - 1))
           {    

                if (isBR(doc.getCharacterElement(em.getStartOffset() - 1).getAttributes()))
                {
                     doc.setOuterHTML(em, brbrp);
                     i = pe.getEndOffset() - 2;
                }

                else
                {
                     doc.insertBeforeEnd(pe, brbr);
                     i = pe.getEndOffset() - 2;
                }
           }

           else
           {
               if (selStart == em.getStartOffset())

               {
                    doc.insertBeforeStart(em, br);
                    i = em.getStartOffset();
               }
               else 

               {
                    addNewLineInTheMiddle(em);
                    i = selStart + 1;
               }
           }

           detach();

           if (outOfRange(i, i))
           {
                i = getStartLoc();
                this.setCaretPosition(i);
                insertCaretPos = i;
                resetUndoRedo();
           }
           else
           {
                this.setCaretPosition(i);
                insertCaretPos = i;
           }
      }

      private void addNewLineInTheMiddle(javax.swing.text.Element em) throws
                        javax.swing.text.BadLocationException, java.io.IOException
      {
          int selStart = this.getCaretPosition();
          String style = getCurrentStyle(em);
          String firstText = doc.getText(em.getStartOffset(), selStart - em.getStartOffset());
          String secondText = doc.getText(selStart, em.getEndOffset() - selStart);
          firstText = cleanText(firstText); 
          secondText = cleanText(secondText);
          firstText = span style= + style +  + destroySpace(firstText) + span;
          secondText = span style= + style +  + destroySpace(secondText) + span;
          doc.setOuterHTML(em, firstText + br + secondText);
      }

      private javax.swing.text.Element
              splitStart (javax.swing.text.Element block, String oldStyle, String newStyle, int selEnd)
      {

           if (block.getAttributes().getAttribute(
                   javax.swing.text.html.HTML.Tag.SPAN) == null)
           {
               return splitContStart(block, newStyle, selEnd);
           }

           try
           {

               if (isBlockEditable(block))
               {

                    String textOldF = this.getText(selEnd, block.getEndOffset() - selEnd);
                    String textNewF = this.getText(block.getStartOffset(),
                            selEnd - block.getStartOffset());
                    textOldF = cleanText(textOldF);
                    textNewF = cleanText(textNewF);

                    String text = span  + newStyle +  + destroySpace(textNewF) + span
                            + span  + oldStyle +  + destroySpace(textOldF) + span;

                    doc.setOuterHTML(block, text);

                }
           }
           catch (javax.swing.text.BadLocationException  java.io.IOException ble) 
           {
                resetUndoRedo();
           }

           return null;
      }

      private javax.swing.text.Element
              splitEnd (javax.swing.text.Element block, String oldStyle, String newStyle, int selStart, int selEnd)
      {

           if (block.getAttributes().getAttribute(
                   javax.swing.text.html.HTML.Tag.SPAN) == null)
           {
               return splitContEnd(block, newStyle, selStart, selEnd);
           }

           int start = block.getStartOffset();
           try
           {
               if (isBlockEditable(block))
               {

                    String textOldF = this.getText(block.getStartOffset(),
                            selStart - block.getStartOffset());
                    String textNewF = this.getText(selStart,
                            block.getEndOffset() - selStart);

                    textOldF = cleanText(textOldF);
                    textNewF = cleanText(textNewF);
                    String text = span  + oldStyle +  + destroySpace(textOldF) + span
                            + span  + newStyle +  + destroySpace(textNewF) + span;

                    doc.setOuterHTML(block, text);

                    block = doc.getCharacterElement(start);

                    block = doc.getCharacterElement(block.getEndOffset());
               }

               if (selEnd  block.getEndOffset())
               {
                    block = doc.getCharacterElement(block.getEndOffset());
               }
               else
               {
                    block = null;
               }
           }
           catch (javax.swing.text.BadLocationException  java.io.IOException ble) 
           {
                resetUndoRedo();
                block = null;
           }
           return block;
      }

      private javax.swing.text.Element splitMod (javax.swing.text.Element block, String newStyle, int selEnd)
      {

           if (block.getAttributes().getAttribute(
                   javax.swing.text.html.HTML.Tag.SPAN) == null)
           {
               return splitContMod(block, newStyle, selEnd);
           }

           try
           {
               if (isBlockEditable(block))
               {
                    String text = this.getText(block.getStartOffset(),
                            block.getEndOffset() - block.getStartOffset());

                    text = cleanText(text);

                    text = span  + newStyle +  + destroySpace(text) + span;

                    doc.setOuterHTML(block, text);

                }

                if (selEnd  block.getEndOffset())
                {
                    block = doc.getCharacterElement(block.getEndOffset());
                }
                else
                {
                    block = null;
                }
           }
           catch (javax.swing.text.BadLocationException  java.io.IOException ble) 
           {
                resetUndoRedo();
                block = null;
           }
           return block;
      }

      private javax.swing.text.Element
              splitMid (javax.swing.text.Element block, String oldStyle, String newStyle, int selStart, int selEnd)
      {

           if (block.getAttributes().getAttribute(
                   javax.swing.text.html.HTML.Tag.SPAN) == null)
           {

               return splitContMid(block, newStyle, selStart, selEnd);
           }

           try
           {

               if (isBlockEditable(block))
               {
                    String textStart = this.getText(block.getStartOffset(),
                            selStart - block.getStartOffset());
                    String textMid = this.getText(selStart, selEnd - selStart);
                    String textEnd = this.getText(selEnd,
                            block.getEndOffset() - selEnd);

                    textStart = cleanText(textStart);
                    textMid = cleanText(textMid);
                    textEnd = cleanText(textEnd);

                    textStart = span  + oldStyle +  + destroySpace(textStart) + span;
                    textMid = span  + newStyle +  + destroySpace(textMid) + span;
                    textEnd = span  + oldStyle +  + destroySpace(textEnd) + span;
                    String text = textStart + textMid + textEnd;

                    doc.setOuterHTML(block, text);
                    this.setSelectionStart(selStart);
                    this.setSelectionEnd(selEnd);

               }
           }
           catch (javax.swing.text.BadLocationException  java.io.IOException ble) 
           {
                resetUndoRedo();
           }
           return null; 
      }

      private javax.swing.text.Element splitContStart (javax.swing.text.Element block, String style, int selEnd)
      {

           try
           {

               if (isBlockEditable(block))
               {
                    String textStart = this.getText(block.getStartOffset(),
                            selEnd - block.getStartOffset());
                    textStart = cleanText(textStart);

                    textStart = span  + style +  + destroySpace(textStart) + span;

                    javax.swing.text.SimpleAttributeSet sas =
                            new javax.swing.text.SimpleAttributeSet();
                    sas.addAttribute(javax.swing.text.html.HTML.Tag.SPAN, );

                    int startLoc = block.getStartOffset(); 

                    doc.remove(block.getStartOffset(), selEnd - block.getStartOffset());
                    doc.insertString(startLoc, textStart, sas);

                    doc.setOuterHTML(doc.getCharacterElement(startLoc), textStart);
                }
           }
           catch (javax.swing.text.BadLocationException  java.io.IOException ble) 
           {
                resetUndoRedo();
           }
           return null;
      }

      private javax.swing.text.Element splitContEnd (javax.swing.text.Element block, String style, int selStart, int selEnd)
      {

           int start = block.getStartOffset();
           try
           {

               if (isBlockEditable(block))
               {
                    String textStart = this.getText(block.getStartOffset(),
                            selStart - block.getStartOffset());
                    String textEnd = this.getText(selStart,  block.getEndOffset() - selStart);

                    textStart = cleanText(textStart);
                    textEnd = cleanText(textEnd);
                    textEnd = span  + style +  + destroySpace(textEnd) + span;

                    javax.swing.text.SimpleAttributeSet sas =
                            new javax.swing.text.SimpleAttributeSet();
                    sas.addAttribute(javax.swing.text.html.HTML.Tag.SPAN, );

                    int startLoc = selStart; 

                    doc.remove(startLoc, block.getEndOffset() - startLoc);
                    doc.insertString(startLoc, textStart, sas);

                    doc.setOuterHTML(doc.getCharacterElement(startLoc), textEnd);

                    block = doc.getCharacterElement(start);

                    block = doc.getCharacterElement(block.getEndOffset());
                }

                if (selEnd  block.getEndOffset())
                {
                    block = doc.getCharacterElement(block.getEndOffset());
                }
                else
                {
                    block = null;
                }
           }
           catch (javax.swing.text.BadLocationException  java.io.IOException ble) 
           {
                resetUndoRedo();
                block = null;
           }
           return block;
      }

      private javax.swing.text.Element
              splitContMid (javax.swing.text.Element block, String style, int selStart, int selEnd)
      {

           int start = block.getStartOffset();
           try
           {

               String text = this.getText(selStart, selEnd - selStart);
               text = cleanText(text);

               text = span  + style +  + destroySpace(text) + span;

               doc.remove(selStart, selEnd - selStart);

               javax.swing.text.SimpleAttributeSet sas = new javax.swing.text.SimpleAttributeSet();
               sas.addAttribute(javax.swing.text.html.HTML.Tag.SPAN, );
               doc.insertString(selStart,   , sas);

               block = doc.getCharacterElement(doc.getCharacterElement(start).getEndOffset());

               doc.setOuterHTML(block, text); 
           }
           catch (javax.swing.text.BadLocationException  java.io.IOException ble) 
           {
                resetUndoRedo();
           }
           return null;
      }

      private javax.swing.text.Element splitContMod (javax.swing.text.Element block, String style, int selEnd)
      {

           int start = block.getStartOffset();
           try
           {

               if (isBlockEditable(block))
               {
                    String text = this.getText(block.getStartOffset(),
                            block.getEndOffset() - block.getStartOffset());

                    text = cleanText(text);
                    text = span  + style +  + destroySpace(text) + span;

                    doc.remove(block.getStartOffset(), block.getEndOffset() 
                            - block.getStartOffset());

                    javax.swing.text.SimpleAttributeSet sas =
                            new javax.swing.text.SimpleAttributeSet();
                    sas.addAttribute(javax.swing.text.html.HTML.Tag.SPAN, );
                    doc.insertString(start,   , sas);

                    block = doc.getCharacterElement(start);

                    doc.setOuterHTML(block, text); 
               }

               if (selEnd  block.getEndOffset())
               {
                    block = doc.getCharacterElement(block.getEndOffset());
               }
               else
               {
                    block = null;
               }
           }
           catch (javax.swing.text.BadLocationException  java.io.IOException ble) 
           {
                resetUndoRedo();
                block = null;
           }
           return block;
      }

      private String getTextSize(javax.swing.text.Element block)
      {

           javax.swing.text.AttributeSet as = block.getAttributes();
           String size;

           if (as.getAttribute(javax.swing.text.html.CSS.Attribute.FONT_SIZE) != null)
           {
                size = as.getAttribute(javax.swing.text.html.CSS.Attribute.FONT_SIZE).toString(); 

                size = size.substring(0, size.length() - 2);
           }
           else
           {
               size = ;
           }

           if (prevSize.equals())
           {
               dispSize = prevSize = size;

           }
           else
           {

               if (!prevSize.equals(size))
               {

                    dispSize = prevSize = ...;
               }
           }
           return size;
      }   

      private String getCurrentFont(javax.swing.text.Element block)
      {
           javax.swing.text.AttributeSet as = block.getAttributes();
           String font;

           if (as.getAttribute(javax.swing.text.html.CSS.Attribute.FONT_FAMILY) != null)
           {
                dispFont = font = as.getAttribute(javax.swing.text.html.CSS.Attribute.FONT_FAMILY).toString(); 
           }
           else
           {
                dispFont =  ;
                font = ;
           }

           if (dispFont.equals( ))
           {
                dispFont =  ;
           }

           if (prevFont.equals())
           {

               prevFont = dispFont;
           }
           else
           {

               if (!prevFont.equals(dispFont))
               {

                    dispFont = .....;
                    prevFont = .....;
               }
           }
           return font;
      }

      private String getCurrentColor(javax.swing.text.Element block)
      {
           javax.swing.text.AttributeSet as = block.getAttributes();

           if (block.getAttributes().containsAttribute(CR, true))
           {
               block = doc.getCharacterElement(block.getStartOffset() - 1);
               as = block.getAttributes();
               if (as.getAttribute(javax.swing.text.html.CSS.Attribute.COLOR) != null)
               {
                    currentColor = as.getAttribute(javax.swing.text.html.CSS.Attribute.COLOR).toString();
               }
               else
               {

                    prevColor = ....;
                    currentColor = #000000;
               }
           }
           else
           {
               if (as.getAttribute(javax.swing.text.html.CSS.Attribute.COLOR) != null)
               {
                    currentColor = as.getAttribute(javax.swing.text.html.CSS.Attribute.COLOR).toString();
               }
               else
               {
                    prevColor = ....;
                    currentColor = #000000;
               }
           }

           if ((currentColor.substring(0, 1).compareTo(#) != 0))
           {

                currentColor = #000000;
           }

           if (prevColor.equals())
           {
               prevColor = currentColor;

               if (!currentColor.toString().substring(0, 1).equals(#))
               {
                    currentColor = ;
               }
           }
           else
           {

               if (!prevColor.equals(currentColor))
               {
                    prevColor = ....;
               }
           }

           return currentColor;
      }

      private String getSubSuperTxt (javax.swing.text.Element block, boolean keepOld)
      {
           byte i = getSubSuper(block, keepOld);
           if (i == 1)
           {
                return SUPERSCRIPT;
           }
           else if (i == 2)
           {
                return SUBSCRIPT;
           }
           else
           {
                return ;
           }
      }

      private byte getSubSuper(javax.swing.text.Element block, boolean keepOld)
      {

           if (keepOld == false)
           {
                isSuper = isSub = false;          
           }
           byte i = 0;
           javax.swing.text.AttributeSet as = block.getAttributes();
           if (as.getAttribute(javax.swing.text.html.CSS.Attribute.VERTICAL_ALIGN) != null)
           {
                String t = as.getAttribute(javax.swing.text.html.CSS.Attribute.VERTICAL_ALIGN).toString();

                if (t.equalsIgnoreCase(SUPERSCRIPT))
                {
                     i = 1;
                     isSuper = true;
                }
                else if (t.equalsIgnoreCase(SUBSCRIPT))
                {
                     i = 2;
                     isSub = true;

                }
           }
           return i;
      }

      private boolean[] showCurrentStyle(javax.swing.text.Element block, boolean keepOld)
      {
           if (block == null)
           {
                return null;
           }

           boolean[] bius = new boolean[]{false, false, false, false};

           javax.swing.text.AttributeSet as = block.getAttributes();

           if (keepOld == false)
           {

               isBold = false;
               isItalic = false;
               isUnderlined = false;
               isStriked = false;
           }

           if (as.getAttribute(javax.swing.text.html.HTML.Tag.SPAN) == null)
           {
               if (keepOld == false)
               {

                    isBold = false;
                    isItalic = false;
                    isUnderlined = false;
                    isStriked = false;
               }
           }

           Object textDecoration =
                   as.getAttribute(javax.swing.text.html.CSS.Attribute.TEXT_DECORATION);
           String textDecorationS;

           if (as.getAttribute(javax.swing.text.html.CSS.Attribute.FONT_WEIGHT) != null)
           {

               bius[0] = true;
               isBold = true;
           }

           if (as.getAttribute(javax.swing.text.html.CSS.Attribute.FONT_STYLE) != null)
           {

               bius[1] = true;
               isItalic = true;
           }

           if (textDecoration != null)
           {
               textDecorationS = textDecoration.toString();
               if (textDecorationS.compareTo(UNDER_AND_THROUGH) == 0)
               {

                    bius[2] = bius[3] = true;
                    isUnderlined = isStriked = true;
               }
               else if (textDecorationS.compareTo(UNDERLINE) == 0)
               {

                    bius[2] = true;
                    isUnderlined = true;
               }
               else if (textDecorationS.compareTo(STRIKETHROUGH) == 0)
               {

                    bius[3] = true;
                    isStriked = true;
               }
           }
           return bius;
      }

      private String getCurrentStyle(javax.swing.text.Element em)
      {
           if (em == null) return ;
           return getCurrentStyle(em.getAttributes());
      }

      private String getCurrentStyle(javax.swing.text.AttributeSet as)
      {

           Object fontFamily =
                   as.getAttribute(javax.swing.text.html.CSS.Attribute.FONT_FAMILY); 
           String fontFamilyS;
           Object color =
                   as.getAttribute(javax.swing.text.html.CSS.Attribute.COLOR); 
           String colorS;
           Object textDecoration =
                   as.getAttribute(javax.swing.text.html.CSS.Attribute.TEXT_DECORATION);
           String textDecorationS;
           boolean italic          = false; 
           boolean bold            = false; 
           boolean underline       = false; 
           boolean strikeThrough   = false; 
           Object fontSize         =
                   as.getAttribute(javax.swing.text.html.CSS.Attribute.FONT_SIZE); 
           String fontSizeS = ;

           if (fontSize != null)
           {
               fontSizeS = fontSize.toString();
           }

           if (fontFamily == null) { fontFamilyS = ;}
           else { fontFamilyS = ' + fontFamily.toString() + ';}

           if (color == null) { colorS = ;}
           else { colorS = color.toString();}

           if (as.getAttribute(javax.swing.text.html.CSS.Attribute.FONT_STYLE) != null)
           {
               italic = true;
           }

           if (as.getAttribute(javax.swing.text.html.CSS.Attribute.FONT_WEIGHT) != null)
           {
               bold = true;
           }

           String vAlign = null;
           if (as.getAttribute(javax.swing.text.html.CSS.Attribute.VERTICAL_ALIGN) != null)
           {
                vAlign = as.getAttribute(javax.swing.text.html.CSS.Attribute.VERTICAL_ALIGN).toString();
           }

           if (textDecoration != null)
           {
               textDecorationS = textDecoration.toString();
               if (textDecorationS.compareTo(UNDER_AND_THROUGH) == 0)
               {
                    underline = true;
                    strikeThrough = true;
               }
               else if (textDecorationS.compareTo(UNDERLINE) == 0)
               {
                    underline = true;
               }
               else if (textDecorationS.compareTo(STRIKETHROUGH) == 0)
               {
                    strikeThrough = true;
               }
           }
           return getStyle
                   (fontFamilyS, colorS, fontSizeS, bold, italic, underline, strikeThrough, vAlign);
      }

      private String modifyCurrentStyle(javax.swing.text.Element block, String[] e)
      {
           javax.swing.text.AttributeSet as = block.getAttributes();
           String fontFamilyS = , colorS = , textDecorationS, fontSizeS = , vAlign = ;

           boolean bold = false;

           boolean italic = false;

           boolean underline = false;
           boolean strike = false;

           if (as.getAttribute(javax.swing.text.html.HTML.Tag.SPAN) != null)
           {

                Object fontFamily = as.getAttribute(javax.swing.text.html.CSS.Attribute.FONT_FAMILY); 
                Object color = as.getAttribute(javax.swing.text.html.CSS.Attribute.COLOR); 
                Object textDecoration = as.getAttribute(javax.swing.text.html.CSS.Attribute.TEXT_DECORATION); 
                Object fontSize = as.getAttribute(javax.swing.text.html.CSS.Attribute.FONT_SIZE); 

                if (fontFamily != null)      { fontFamilyS = ' + fontFamily.toString() + ';} 
                if (color != null)           { colorS = color.toString();} 
                if (fontSize != null) { fontSizeS = fontSize.toString();}
                if (as.getAttribute(javax.swing.text.html.CSS.Attribute.FONT_WEIGHT) != null)
                     bold = true; 
                if (as.getAttribute(javax.swing.text.html.CSS.Attribute.FONT_STYLE) != null)
                     italic = true; 
                if (textDecoration != null)
                {
                     textDecorationS = textDecoration.toString();
                     if (textDecorationS.compareTo(UNDERLINE) == 0)
                     {
                         underline = true;
                     }
                     else if (textDecorationS.compareTo(STRIKETHROUGH) == 0)
                     {
                         strike = true;
                     }
                     else if (textDecorationS.compareTo(UNDER_AND_THROUGH) == 0)
                     {
                         underline = true;
                         strike = true;
                     }
                }

                if (as.getAttribute(javax.swing.text.html.CSS.Attribute.VERTICAL_ALIGN) != null)
                {
                    vAlign = as.getAttribute(javax.swing.text.html.CSS.Attribute.VERTICAL_ALIGN).toString();
                }
           }

           if (e.length == 1) 
           {
               if (e[0].compareTo(BOLD) == 0) { bold = true; }
               else if (e[0].compareTo(NO_BOLD) == 0) { bold = false; }
               else if (e[0].compareTo(ITALIC) == 0) { italic = true; }
               else if (e[0].compareTo(NO_ITALIC) == 0) { italic = false; }
               else if (e[0].compareTo(UNDERLINE) == 0) { underline = true; }
               else if (e[0].compareTo(NO_UNDERLINE) == 0) { underline = false; }
               else if (e[0].compareTo(STRIKETHROUGH) == 0) { strike = true; }
               else if (e[0].compareTo(NO_STRIKETHROUGH) == 0) { strike = false; }

               else if (e[0].compareTo(FONT) == 0) { fontFamilyS = ; }

               else if (e[0].compareTo(COLOR) == 0) { colorS = ; }
           }
           else if (e.length == 2) 
           {
                if (e[0].compareTo(FONT) == 0) { fontFamilyS = ' + e[1] + '; }
                else if (e[0].compareTo(COLOR) == 0) { colorS = e[1]; }
                else if (e[0].compareTo(SIZE) == 0) { fontSizeS = e[1] + px; }
                else if (e[0].compareTo(VERTICAL_ALIGN) == 0) {
                {
                          vAlign = e[1];
                          if (vAlign.length()  0)
                          {                              
                               vAlign = e[1];
                          }
                     }
                }
           }
           else
           {

           }
           return getStyle(fontFamilyS, colorS, fontSizeS, bold, italic, underline, strike, vAlign);
      }

      private String getStyle(
               String fnt, String clr, String size, boolean b, boolean i, boolean u, boolean s, String vAlign)
      {
             String fontFamily = FONT +  + fnt + ;;
             String fontSize = font-size + size + ;;
             String color = COLOR +  + clr + ;;
             String bold = WEIGHT +  + BOLD + ;;
             String italic =  STYLE +  + ITALIC + ;; 
             String dec = ;
             String underline = DECORATION +  + UNDERLINE + ;;
             String strike = DECORATION +  + STRIKETHROUGH + ;;
             String underlineAndStrike = DECORATION +  + UNDER_AND_THROUGH + ;;
             if (vAlign == null)
             { 
                  vAlign = ;
             }
             else
             {
                  if (vAlign.length()  0)
                  {
                       vAlign = VERTICAL_ALIGN +  + vAlign + ;;
                  } 
             }

             if (fnt.length() == 0) { fontFamily = ;}
             if (clr.length() == 0) {color = ;}
             if (size.length() == 0) {fontSize = ;}
             if (!b) {bold = ;}
             if (!i) {italic = ;}

             if (u && !s) {dec = underline;}
             if (!u && s) {dec = strike;}
             if (u && s){dec = underlineAndStrike;}
             return fontFamily + color + fontSize + bold + italic + dec + vAlign;
      }

      private String getParaStyle (javax.swing.text.AttributeSet as)
      {
           String t;
           Object attyPstyle;

           if (as == null) return left;

           if (as.getAttribute(javax.swing.text.html.CSS.Attribute.TEXT_ALIGN) != null)
           {
                attyPstyle = as.getAttribute(javax.swing.text.html.CSS.Attribute.TEXT_ALIGN);
           }
           else
           {
                return left;
           }

           if (attyPstyle != null)
           {
               t = attyPstyle.toString();
           }
           else
           {

               t = left;
           }
           return t; 
      }

      private void showParagraphStyle(javax.swing.text.Element block, boolean keepOld)
      {    
           if (!keepOld)
           {
               isParaLeft = false; 
               isParaRight = false; 
               isParaCenter = false; 
               isParaJustify = false;
           }

           block = doc.getParagraphElement(block.getStartOffset());

           String t = getParaStyle(block.getAttributes());

           if (t.equalsIgnoreCase(left))
           {
                isParaLeft = true;
           }
           else if (t.equalsIgnoreCase(right))
           {
                isParaRight = true;
           }
           else if (t.equalsIgnoreCase(center))
           {
                isParaCenter = true;
           }
           else if (t.equalsIgnoreCase(justify))
           {
                isParaJustify = true;
           }
           else
           {
                isParaLeft = true;
           }
      }

      private String destroyNBSP (String t)
      {
           String c = t.substring(0, 1);

           if (c.equalsIgnoreCase( )  c.equalsIgnoreCase(u00a0))
           {
                t = &nbsp; + t.substring(1);
           }

           t = t.replace( u00a0,  &nbsp;);
           t = t.replace(  ,  &nbsp;);
           t = t.replace(u00a0u00a0,  &nbsp;);
           return t;
      }

      private String destroySpace(String t)
      {          
          if (t.length()  1)
          {

               if (t.substring(0, 1).matches( ))
               {

                    t = &nbsp; + t.substring(1);
               }
               t = t.replace(  ,  &nbsp;);
          }
          else if (t.length() == 1)
          {
               if (t.matches( ))
               {
                    t = &nbsp;;
               }
          }
          return t;
      }

      private javax.swing.text.Element getBlock(int selStart) 
      {
          return doc.getCharacterElement(selStart);
      }

      public void wt(String text)
      {
          if (this.out == null) return;

          try
          {
               out.getDocument().insertString(
                    out.getDocument().getEndPosition().getOffset() - 1, text, null);
          }
          catch (javax.swing.text.BadLocationException ex){}
      }

      private int txtGetX() {return theJSP.getLocationOnScreen().x + 5;}

      private int txtGetY() {return theJSP.getLocationOnScreen().y + 5;}

      private void insertSpec()
      {
           if (outOfRange(getSelectionStart(), getSelectionEnd()))
           {
                return;
           }
           SpecChar z = new SpecChar();
           centrizeMe(z);
           z.setVisible(true);
      }

      private javax.swing.JPopupMenu getPopMenu()
      {
          javax.swing.JMenu menuTextStyle = new javax.swing.JMenu(Text style);
          javax.swing.JMenu menuParaStyle = new javax.swing.JMenu(Text align);

          javax.swing.JMenu menuIndent = new javax.swing.JMenu(Indent);
          javax.swing.JMenu menuSearchReplace = new javax.swing.JMenu(SearchReplace);

          javax.swing.JMenuItem mSearch = new javax.swing.JMenuItem(Search);
          javax.swing.JMenuItem mReplace = new javax.swing.JMenuItem(Replace);

          mSearch.setActionCommand(search);
          mReplace.setActionCommand(replace);

          mSearch.addActionListener(eventHandler);
          mReplace.addActionListener(eventHandler);

          mSearch.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F, java.awt.event.InputEvent.CTRL_MASK));

          menuSearchReplace.add(mSearch);
          menuSearchReplace.add(mReplace);

          javax.swing.JMenuItem wCount = new javax.swing.JMenuItem(Word count);

          wCount.setActionCommand(wcount);

          wCount.addActionListener(eventHandler);

          javax.swing.JMenuItem b = new javax.swing.JMenuItem(Bold);
          javax.swing.JMenuItem i = new javax.swing.JMenuItem(Italic);
          javax.swing.JMenuItem u = new javax.swing.JMenuItem(Underline);
          javax.swing.JMenuItem s = new javax.swing.JMenuItem(Strikethrough);
          b.setActionCommand(BOLD);
          i.setActionCommand(ITALIC);
          u.setActionCommand(UNDERLINE);
          s.setActionCommand(STRIKETHROUGH);

          b.addActionListener(eventHandler);
          i.addActionListener(eventHandler);
          u.addActionListener(eventHandler);
          s.addActionListener(eventHandler);

          b.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_B, java.awt.event.InputEvent.CTRL_MASK));
          i.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_I, java.awt.event.InputEvent.CTRL_MASK));
          u.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_U, java.awt.event.InputEvent.CTRL_MASK));
          s.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));

          javax.swing.JMenuItem paraL = new javax.swing.JMenuItem(Left);
          javax.swing.JMenuItem paraR = new javax.swing.JMenuItem(Right);
          javax.swing.JMenuItem paraC = new javax.swing.JMenuItem(Center);
          javax.swing.JMenuItem paraJ = new javax.swing.JMenuItem(Justify);
          paraL.setActionCommand(PARA_LEFT);
          paraR.setActionCommand(PARA_RIGHT);
          paraC.setActionCommand(PARA_CENTER);
          paraJ.setActionCommand(PARA_JUSTIFY);

          paraL.addActionListener(eventHandler);
          paraR.addActionListener(eventHandler);
          paraC.addActionListener(eventHandler);
          paraJ.addActionListener(eventHandler);

          paraL.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_L, java.awt.event.InputEvent.CTRL_MASK));
          paraR.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, java.awt.event.InputEvent.CTRL_MASK));
          paraC.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_M, java.awt.event.InputEvent.CTRL_MASK));
          paraJ.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_J, java.awt.event.InputEvent.CTRL_MASK));

          javax.swing.JMenuItem menuColor = new javax.swing.JMenuItem(Color);
          javax.swing.JMenuItem menuFont = new javax.swing.JMenuItem(Font);

          menuColor.setActionCommand(COLOR);
          menuFont.setActionCommand(dlg + FONT);

          menuColor.addActionListener(eventHandler);
          menuFont.addActionListener(eventHandler);

          menuColor.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_K, java.awt.event.InputEvent.CTRL_MASK));

          javax.swing.JMenuItem menuSub =  new javax.swing.JMenuItem(Subscript);
          javax.swing.JMenuItem menuSuper =  new javax.swing.JMenuItem(Superscript);

          menuSub.setActionCommand(SUBSCRIPT);
          menuSuper.setActionCommand(SUPERSCRIPT);

          menuSub.addActionListener(eventHandler);
          menuSuper.addActionListener(eventHandler);

          javax.swing.JMenuItem menuCopy = new javax.swing.JMenuItem(Copy);
          javax.swing.JMenuItem menuCut = new javax.swing.JMenuItem(Cut);
          javax.swing.JMenuItem menuPaste = new javax.swing.JMenuItem(Paste);

          menuCopy.setActionCommand(kopy);
          menuCut.setActionCommand(kut);
          menuPaste.setActionCommand(paste);

          menuCopy.addActionListener(eventHandler);
          menuCut.addActionListener(eventHandler);
          menuPaste.addActionListener(eventHandler);

          menuCopy.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.CTRL_MASK));
          menuCut.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_X, java.awt.event.InputEvent.CTRL_MASK));
          menuPaste.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_V, java.awt.event.InputEvent.CTRL_MASK));

          javax.swing.JMenuItem menuUndo = new javax.swing.JMenuItem(Undo);
          javax.swing.JMenuItem menuRedo = new javax.swing.JMenuItem(Redo);

          menuUndo.setActionCommand(undo);
          menuRedo.setActionCommand(redo);

          menuUndo.setEnabled(urMgr.undoable);
          menuRedo.setEnabled(urMgr.redoable);

          menuUndo.addActionListener(eventHandler);
          menuRedo.addActionListener(eventHandler);

          menuUndo.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Z, java.awt.event.InputEvent.CTRL_MASK));
          menuRedo.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Y, java.awt.event.InputEvent.CTRL_MASK));

          javax.swing.JMenuItem menuIncIndent = new javax.swing.JMenuItem(Increase indent); 
          javax.swing.JMenuItem menuDecIndent = new javax.swing.JMenuItem(Decrease indent); 

          menuIncIndent.setActionCommand(increase indent);
          menuDecIndent.setActionCommand(decrease indent);

          menuIncIndent.addActionListener(eventHandler);
          menuDecIndent.addActionListener(eventHandler);

          javax.swing.JMenuItem specChar = new javax.swing.JMenuItem(Insert special characters);

          specChar.setActionCommand(specar);

          specChar.addActionListener(eventHandler);

          menuTextStyle.add(b);
          menuTextStyle.add(i);
          menuTextStyle.add(u);
          menuTextStyle.add(s);

          menuParaStyle.add(paraL);
          menuParaStyle.add(paraR);
          menuParaStyle.add(paraC);
          menuParaStyle.add(paraJ);

          menuIndent.add(menuIncIndent);
          menuIndent.add(menuDecIndent);

          javax.swing.JPopupMenu menu = new javax.swing.JPopupMenu();
          menu.add(menuTextStyle);
          menu.add(menuColor);
          if (fntDlg != null)
          {
               menu.add(menuFont);
          }
          menu.add(menuSub);
          menu.add(menuSuper);
          menu.add(new javax.swing.JSeparator());
          menu.add(menuParaStyle);
          menu.add(menuIndent);
          menu.add(new javax.swing.JSeparator());
          menu.add(menuCopy);
          menu.add(menuCut);
          menu.add(menuPaste);
          menu.add(new javax.swing.JSeparator());
          menu.add(specChar);
          menu.add(new javax.swing.JSeparator());
          menu.add(menuUndo);
          menu.add(menuRedo);
          menu.add(new javax.swing.JSeparator());
          menu.add(menuSearchReplace);
          menu.add(wCount);

          menu.addPopupMenuListener(eventHandler);
          return menu;
      }

      private String cleanText(String text)
      {
          text = text.replace(&, &amp;);
          text = text.replace(, &lt;);
          text = text.replace(, &gt;);
          text = text.replace(, &quot;);
          return text;
      }

      private int blank_(int currentLoc)
      {
          String currentChar;

          try
          {
               currentChar = doc.getText(currentLoc, 1);
               while (true)
               {
                    if (currentChar.equals( )  currentChar.equals(u00a0)  currentChar.equals(n))
                    {
                         break;
                    }
                    else
                    {
                         currentChar = doc.getText(++currentLoc, 1);
                    }
               }
          }
          catch (javax.swing.text.BadLocationException ble)
          {}
          return currentLoc;
      }

      private int[] getWordCount(int start, int end)
              throws javax.swing.text.BadLocationException
      {

          int xStart = start, xEnd = end,
                  count = 0, countC = 0, countNS = 0, i;

          if (start == end)
          {
               xEnd = end = doc.getLength();
          }

          String currentChar;

          i = 0;
          while (start  end)
          {
               currentChar = doc.getText(start, 1);
               if (!currentChar.equals(n))
               {
                    countC++;
               }
               start++;
               if (i++  getEndLoc()) break;

          }

          start = xStart;
          end = xEnd;
          i = 0;
          while (start  end)
          {
               currentChar = doc.getText(start, 1);
               if (!(currentChar.equals( )  currentChar.equals(u00a0)  currentChar.equals(n)))
               {
                    countNS++;
               }
               start++;
               if (i++  getEndLoc()) break;
          }

          start = xStart;
          end = xEnd;

          i = 0;
          while (start  end)
          {
               currentChar = doc.getText(start, 1);

               if (currentChar.equals( )  currentChar.equals(u00a0)  currentChar.equals(n))
               {
                    start++;
               }
               else 
               {
                    count++;
                    start = blank_(start);
               }
               if (i++  getEndLoc()) break;
          }

          return new int[]{countC, countNS, count};
      }

      public int[] countWord(boolean showDlg)
      {
           int[] s, a, r;
           try
           {
                if (outOfRange(getSelectionStart(), getSelectionEnd()))
                {
                     a = s = new int[]{0, 0, 0};
                }
                else
                {
                     if (getSelectionEnd() == getSelectionStart())
                     {
                          s = new int[]{0, 0, 0};
                     }
                     else
                     {
                          s = getWordCount(getSelectionStart(), getSelectionEnd());
                     }
                     a = getWordCount(getStartLoc(), getEndLoc());
                }
                r = new int[]{a[0], a[1], a[2], s[0], s[1], s[2]};
                if (showDlg)
                {
                    WordCount wcDlg = 

                            new WordCount(r);
                    centrizeMe(wcDlg);
                    wcDlg.setVisible(true);
                }
           }
           catch (javax.swing.text.BadLocationException ble)
           {
                r = null;
           }
           return r;         
      }

      private int replaceAll(String key, boolean exact, boolean cs, String replacement, int start, int end)
      {
           int i;
           try
           {
                i = execReplaceAll(key, exact, cs, replacement, start, end);
           }
           catch (java.lang.IndexOutOfBoundsException e)
           {
                resetSelection(start, start, false);
                i = 0;
                resetUndoRedo();
           }
           return i;
      }

      private int execReplaceAll(String key, boolean exact, boolean cs, String replacement, int start, int end)
              throws java.lang.IndexOutOfBoundsException
      {
           boolean b = forwardSelection(), c = true;

           if (start  doc.getLength()  start  end) return 0;

           if (start == end)
           {
                end = getEndLoc();
                c = false;
           }

           reflectStyle = false;

           int[] searchResult;

           int i = 0, cPos = start, diff = (replacement.length() - key.length());
           replaceUndoRedo = new ReplaceUndoRedo(start);

           if ((searchResult = execSearch(key, exact, cs, start, end, true)) != null)
           {

                processDeleteSelection(start, end);
                replaceUndoRedo.addBeforeState(bur, end);
           }
           else
           {
                return 0;
           }

           try
           {
               while(true)
               {
                    i++;
                    insertText(replacement, searchResult[0] + key.length(), null);
                    deleteText(searchResult[0], searchResult[0] + key.length());

                    start = searchResult[0] + replacement.length();
                    end = end + diff;
                    searchResult = execSearch(key, exact, cs, start, end, true);

                    if (searchResult == null  (start = end))
                    {
                         break;
                    }
               }

               processDeleteSelection(cPos, end);
               replaceUndoRedo.addAfterState(bur, end);

               if (i  0)
               {
                   if (this.prevOp == REPLACE)
                   {
                       this.prevOp = CARET_MOVE;
                   }
                   recOp(REPLACE, prevOp);
               }
               else
               {

                    deleteURInstance = null;
               }
           }
           catch (javax.swing.text.BadLocationException ble)
           {
                c = false;
                replaceUndoRedo = null;
                resetUndoRedo();
                i = -1;
           }

           reflectStyle = true;
           if (c)
           {
                resetSelection(cPos, end, b);
           }
           else
           {
                this.setCaretPosition(start);
           }
           this.searchRecord = null;
           return i;
      }

      private boolean replaceCurrent(String replacement)
      {
           boolean b = forwardSelection();
           int sloc = this.getSelectionStart(), 
                   eloc = this.getSelectionStart() + replacement.length();
           replaceUndoRedo = new ReplaceUndoRedo(sloc);

           if (searchRecord == null 
                   this.getSelectionStart() == this.getSelectionEnd())
           {
                searchRecord = null;
                return false;
           }

           String key = searchRecord.searchKey;

           processDeleteSelection(this.getSelectionStart(), this.getSelectionEnd());
           replaceUndoRedo.addBeforeState(bur, this.getSelectionEnd());

           try
           {
                insertText(replacement, searchRecord.foundLocation[searchRecord.currentIndex] + key.length(), null);
                deleteText(searchRecord.foundLocation[searchRecord.currentIndex],
                        searchRecord.foundLocation[searchRecord.currentIndex] + key.length()); 

                processDeleteSelection(sloc, eloc);
                replaceUndoRedo.addAfterState(bur, eloc);

                if (this.prevOp == REPLACE)
                {
                    this.prevOp = CARET_MOVE;
                }
                recOp(REPLACE, prevOp);
                resetSelection(sloc, eloc, b);
           }
           catch (javax.swing.text.BadLocationException ble)
           {
                resetUndoRedo();
                if (outOfRange(sloc, sloc))
                {
                     this.setCaretPosition(getStartLoc());
                }
                else
                {
                     this.setCaretPosition(sloc);
                }
           }
           searchRecord = null;
           return true;
      }

      private int[] execSearch (String key, boolean exact, boolean cs, int st, int end, boolean first)
              throws java.lang.IndexOutOfBoundsException
      {   
          if (st  end) return null;

          if (st == end)
          {
               end = doc.getLength();
          }

          String text = myGetText(st,  end - st), k;

          if (text == null)
          {
               return null;
          }

          if (!cs)
          {
               text = text.toLowerCase();
               k = key.toLowerCase();
          }
          else
          {
               k = key;
          }

          text = text.replace(u00a0,  );
          text = text.replace(n,  );

          int index,
                  start = 0,
                  searchCount = 0;
          boolean next;

          int i = 0;

          do
          {
               next = false;

               index = text.indexOf(k, start);

               if (index = 0)
               {

                    start = index + 1;
                    searchCount++;
                    next = true;
               }

               if (i++  text.length()) 
               {
                    searchCount = 0;
                    break;
               }
          } while (next);

          if (searchCount == 0) return null;

          boolean startTrue;
          start = 0;
          i = 0;
          int[] foundIndex = new int[searchCount]; 
          searchCount = 0;
          do
          {
               next = false;

               index = text.indexOf(k, start);

               if (index = 0)
               {

                    if (exact) 
                    {

                         if (index - 1  0)
                         {
                              startTrue = true;
                         }
                         else
                         {
                              startTrue = charExists(text.substring(index - 1, index));
                         }

                         if (index + key.length() = text.length())
                         {
                              if (startTrue)
                              {
                                   foundIndex[searchCount++] = index;
                              }
                         }
                         else
                         {
                              startTrue = startTrue && charExists
                                      (text.substring(index + key.length(), index + key.length() + 1));
                              if (startTrue)
                              {
                                   foundIndex[searchCount++] = index;
                              }
                         }
                    } 
                    else
                    {
                         foundIndex[searchCount++] = index;
                    }

                    start = index + 1;
                    next = true;
               }

               if (searchCount = 1 && first) break;

               if (i++  text.length()) 
               {
                    searchCount = 0;
                    break;
               }

          } while (next);

          if (searchCount == 0) return null;

          int[] rtn = new int[searchCount];
          i = 0;
          while (i  searchCount)
          {

               rtn[i] = foundIndex[i] + st ;
               i++;
          }

          return rtn;
      }

      private int[] searchWord (String key, boolean sh, boolean cs, int start, int end, boolean first)
              throws java.lang.IndexOutOfBoundsException
      {
           int selStart, selEnd;
           selStart = this.getSelectionStart();
           selEnd = this.getSelectionEnd();
           boolean fs = forwardSelection();

           if (key.length() == 0)
           {
               searchRecord = null;
               return null;
           }

           int[] searchRes = execSearch(key, sh, cs, start, end, first); 

           if (searchRes != null)
           {
               searchRecord = new SearchRecord();
               searchRecord.foundLocation = searchRes;
               searchRecord.searchKey = key;
               searchRecord.searchWhole = sh;
               searchRecord.caseSensitive = cs;

               requestFocusInWindow();
               this.setSelectionStart(searchRecord.foundLocation[searchRecord.currentIndex]);
               this.setSelectionEnd(
                    searchRecord.foundLocation[searchRecord.currentIndex] + key.length());
               return searchRecord.foundLocation;
           }
           else
           {
                requestFocusInWindow();
                resetSelection(selStart, selEnd, fs);
                searchRecord = null;
                return null;
           }
      }

      private void searchNext(String key, boolean sh, boolean cs, int start, int end, boolean first)
      {
           try
           {
               if (searchRecord != null)
               {

                    if (!isSearchTermTheSame(key, sh, cs))
                    {
                         searchWord(key, sh, cs, start, end, first);
                    }

                    int loc = searchRecord.currentIndex;
                    requestFocusInWindow();

                    if (searchRecord.foundLocation.length == 1)
                    {
                         loc = searchRecord.currentIndex;
                         this.setSelectionStart(searchRecord.foundLocation[loc]);
                         this.setSelectionEnd(searchRecord.foundLocation[loc] + 
                                 searchRecord.searchKey.length());
                    }
                    else
                    {
                         loc = loc + 1;
                         if (loc = searchRecord.foundLocation.length)
                         {
                              loc = 0;
                         }
                         this.setSelectionStart(searchRecord.foundLocation[loc]);
                         this.setSelectionEnd(searchRecord.foundLocation[loc] + 
                                 searchRecord.searchKey.length());
                         searchRecord.currentIndex = loc;
                    }
               }
               else
               {
                    if (start == end)
                    {
                         end = getEndLoc();
                    }
                    searchWord(key, sh, cs, start, end, first);
               }
           }
           catch (java.lang.IndexOutOfBoundsException e)
           {
                resetSelection(start, start, false);
           }
      }

      private void searchPrevious(String key, boolean sh, boolean cs, int start, int end, boolean first)
      {
           try
           {
               if (searchRecord != null)
               {

                    if (!isSearchTermTheSame(key, sh, cs))
                    {
                         searchWord(key, sh, cs, start, end, first);
                    }

                    int loc = searchRecord.currentIndex;
                    requestFocusInWindow();
                    if (searchRecord.foundLocation.length == 1)
                    {
                         loc = searchRecord.currentIndex;
                         this.setSelectionStart(searchRecord.foundLocation[loc]);
                         this.setSelectionEnd(searchRecord.foundLocation[loc] + 
                                 searchRecord.searchKey.length());
                    }
                    else
                    {
                         loc = loc - 1;
                         if (loc  0)
                         {
                              loc = searchRecord.foundLocation.length - 1;
                         }
                         this.setSelectionStart(searchRecord.foundLocation[loc]);
                         this.setSelectionEnd(searchRecord.foundLocation[loc] + 
                                 searchRecord.searchKey.length());
                         searchRecord.currentIndex = loc;
                    }
               }
               else
               {
                    if (start == end)
                    {
                         start = getStartLoc();
                    }
                    searchWord(key, sh, cs, start, end, first);
               }
           }
           catch (java.lang.IndexOutOfBoundsException e)
           {
                resetSelection(start, start, false);
           }
      }        

      private boolean isSearchTermTheSame(String key, boolean sh, boolean cs)
      {
           boolean b;
           b = (key.compareTo(searchRecord.searchKey) == 0);

           if (searchRecord.searchWhole != sh) b = false;
           if (searchRecord.caseSensitive != cs) b = false;
           return b;
      }

      private boolean charExists (String k)
      {
          return !Character.isLetterOrDigit(Character.codePointAt(k.toCharArray(), 0));   
      }

      private boolean isFirstPara(javax.swing.text.Element e)
      {

           javax.swing.text.Element firstPara = 
                   doc.getParagraphElement(doc.getDefaultRootElement().getStartOffset());
           if (isP_Implied(firstPara.getAttributes()))
           {

                firstPara = doc.getParagraphElement(firstPara.getEndOffset());
                return e == firstPara;
           }
           else
           {
                return e == firstPara;
           }
      }

      private boolean isP_Implied(javax.swing.text.AttributeSet as)
      {

           return as.containsAttribute(javax.swing.text.StyleConstants.NameAttribute, javax.swing.text.html.HTML.Tag.IMPLIED);

      }

      public synchronized String getContent(boolean t)
      {
          String txt, bodyTag;
          doc.readLock();

          javax.swing.text.Element em;

          String fnt, size, alignment;

          try
          {
               em = doc.getElement(doc.getDefaultRootElement(), 
                        javax.swing.text.Style.NameAttribute, javax.swing.text.html.HTML.Tag.BODY);

               bodyTag = style=;
               fnt = getCurrentFont(em);
               if (fnt.length()  0)
               {
                    bodyTag = bodyTag + font-family' + fnt + ';;
               }

               size = getTextSize(em);
               if (size.length()  0)
               {
                    bodyTag = bodyTag + font-size + size + px;;
               }

               alignment = getParaStyle(em.getAttributes());
               bodyTag = bodyTag + text-align + alignment + ;;

               if (t)
               {
                    txt = htmlbody  + bodyTag + doGetPartHTML(0, doc.getDefaultRootElement().getEndOffset()) + bodyhtml;
               }
               else
               {
                    txt = doGetPartHTML(0, doc.getDefaultRootElement().getEndOffset());
               }
          }
          finally
          {
               doc.readUnlock();
          }
          return txt;
      }

      private String getPartHTML()
      {
          return getPartHTML(this.getSelectionStart(), this.getSelectionEnd());
      }

      private String getPartHTML(int start, int end)
      {
           return doGetPartHTML(start, end);
      }

      private String doGetPartHTML(int start, int end)
      {
           String HTML  = , paraAlign;
           javax.swing.text.Element pe;

           pe = doc.getParagraphElement(start);
           int i = 0;
           while (true)
           {
               if (i  doc.getLength()) { break;}

               paraAlign = p style=text-align + getParaStyle(pe.getAttributes()) + ;;
               if (getParagraphIndent(pe).length() == 0)
               {
                    paraAlign = paraAlign + ;
               }
               else
               {
                    paraAlign = paraAlign + margin-left + getParagraphIndent(pe) + ;;
               }

               if (end = pe.getEndOffset())
               {
                    if (!isP_Implied(pe.getAttributes()))
                    {
                         isFirstPara(pe);

                         HTML = HTML + paraAlign + getContBlocks(pe, start, end) + p;
                    }
                    break;  
               }
               else
               {
                    if (!isP_Implied(pe.getAttributes()))
                    {
                         isFirstPara(pe);

                         HTML = HTML + paraAlign + getContBlocks(pe, start, end) + p;
                    }
                    pe = doc.getParagraphElement(pe.getEndOffset());
               }                       
               i++;
           }
           return HTML;
      }

      private String getBlockContent (javax.swing.text.Element block, int start, int end)
      {
          String text = getText(block, false);

          if(start  block.getStartOffset())
          {
               start = 0;
          }
          else
          {
               start = start - block.getStartOffset();
          }

          if (end  block.getEndOffset())
          {
               end = block.getEndOffset() - block.getStartOffset();
          }
          else
          {
               end = end - block.getStartOffset();
          }

          if (text.length()  0)
          {
               text = text.substring(start, end);
               return cleanText(text); 
          }
          else
          {
               return ;
          }

      }

      private String genText(javax.swing.text.Element blocks[], int bi, String txt, int start, int end)
      {
           txt = txt + span style= + getCurrentStyle(blocks[0]) + ;
           String nt = ; 
           int i = 0;

           while (i  bi)
           {
                nt = nt + getBlockContent(blocks[i++], start, end);
           }

           if (nt.length()  0)
           {
                txt = txt + destroyNBSP(nt) + span;
           }
           else
           {
                txt = txt + span;
           }
           return txt;
      }

      private String getContBlocks (javax.swing.text.Element para, int start, int end)
      {
           String text, prevStyle;
           text = ;
           int bc = 0;
           javax.swing.text.Element blocks[] = new javax.swing.text.Element[para.getElementCount()];

           if (start  para.getStartOffset()) { start = para.getStartOffset();}
           if (end  para.getEndOffset()) { end = para.getEndOffset();}

           javax.swing.text.Element block;
           block = doc.getCharacterElement(start);
           prevStyle = getCurrentStyle(block);

           do
           {
                if (!block.getAttributes().containsAttribute(CR, true))
                {
                    if (isBR(block.getAttributes()))
                    {
                         if (bc  0)
                         {
                              text = genText(blocks, bc, text, start, end);
                              bc = 0;
                         }
                         text = text + br;
                    }
                    else
                    {

                         if (!getCurrentStyle(block).equalsIgnoreCase(prevStyle) && bc  0)
                         {
                              text = genText(blocks, bc, text, start, end);
                              bc = 0;
                         }
                         blocks[bc++] = block;
                         prevStyle = getCurrentStyle(block);
                    }
                }                

                if (end  block.getEndOffset())
                {
                     block = doc.getCharacterElement(block.getEndOffset());
                }
                else
                {
                     if (bc  0)
                     {
                         text = genText(blocks, bc, text, start, end);
                     }
                     break;
                }
           } while (true);

           return text;
      }

      private String getText(javax.swing.text.Element block, boolean clean)
      {
          String text;
          try
          {
               text = doc.getText(block.getStartOffset(),
                       block.getEndOffset() - block.getStartOffset());
          }
          catch (javax.swing.text.BadLocationException ble)
          {
               text = null;
          }

          if (clean) text = cleanText(text); 

          return text;
      }

      private String myGetText (int offset, int length)
      {
           String txt;
           try
           {
                txt = doc.getText(offset, length);
           }
           catch (javax.swing.text.BadLocationException ble)
           {
                txt = null;
           }
           return txt;
      }

     @Override

     public void paste()
     {
          String leString;
          java.awt.datatransfer.Clipboard cBoard =
                  java.awt.Toolkit.getDefaultToolkit().getSystemClipboard();
          java.awt.datatransfer.Transferable trf = cBoard.getContents();

          int start = getSelectionStart();
          try
          {
               leString = (String) trf.getTransferData(DataFlavor.stringFlavor);
               if (leString != null)
               {
                    if (!noSelection())
                    {
                         processDeleteSelection();
                         recOp(DELETE_SELECTION, prevOp);
                    }
                    leString = leString.replace(n, );
                    leString = leString.replace(r, );

                    insertText(leString, null);
                    pSQ = false;
                    reflectStyle = true;
                    setCaretPosition(start + leString.length());
                    processStyleQueue(leString.length());
               }
          }
          catch (java.awt.datatransfer.UnsupportedFlavorException  java.io.IOException blab)
          {               
               resetStyleQ();
          }

     }

     @Override

     public void cut()
     {
          resetStyleQ();
          if (noSelection()) return;
          copy();
          try
          {
               processDeleteSelection();
               deleteText(getSelectionStart(), getSelectionEnd());
               recOp(DELETE_SELECTION, prevOp);
          }
          catch (javax.swing.text.BadLocationException ble){}
     }

      private void artfFocusGained(FocusEvent fe)
      {

           this.setComponentPopupMenu(getPopMenu());
           if (outOfRange(getCaretPosition(), getCaretPosition()))
           {
                this.setCaretPosition(getStartLoc());
           }
      }

      private void artfFocusLost(FocusEvent fe)
      {
           this.setComponentPopupMenu(null);
      }

      private byte newLineParaString()
      {
           if(isBR(globAttySet)) 
           {
                return -6;
           }
           else if (globAttySet.containsAttribute(CR, true))
           {
                return 6;
           }
           else
           {
                return 0;
           }
      }

      private void processDeleteSelection()
      {
           processDeleteSelection(this.getSelectionStart(), this.getSelectionEnd());
      }

      private void processDeleteSelection(int start, int end)
      {
           String text;
           text = getPartHTML( 
                   doc.getParagraphElement(start).getStartOffset(),
                   doc.getParagraphElement(end).getEndOffset());
           bur = new DeleteSelUndoRedo(start, end, text);
      }

      private void dealWithBR (byte nlps, boolean forward, java.awt.event.KeyEvent ket)
      {
           int loc = this.getCaretPosition();
           javax.swing.text.Element block, pBlock, pe;

           block = doc.getCharacterElement(loc);

           pBlock = doc.getCharacterElement(block.getStartOffset() - 1);
           pe = doc.getParagraphElement(loc);

           if (ket.getKeyCode() == KeyEvent.VK_BACK_SPACE)
           {

                deleteURInstance.addBlock(new DeletedPart(deleteString, prevGlobAttySet), forward);

                if (isBR(pBlock.getAttributes()) &&
                        (pBlock.getStartOffset() == pe.getStartOffset()) && deleteBlankPara)
                {

                    deleteURInstance.addBlock(new DeletedPart(nlps,(byte) 2, prevGlobAttySet), forward);
                }
                else
                {
                    deleteURInstance.addBlock(new DeletedPart(nlps,(byte) 1, prevGlobAttySet), forward);
                }
           }
           else if (ket.getKeyCode() == KeyEvent.VK_DELETE)
           {

                if (block.getEndOffset() == 
                        doc.getParagraphElement(loc).getEndOffset() - 1)
                {

                     deleteURInstance.addBlock(new DeletedPart(deleteString, prevGlobAttySet), forward);
                     deleteURInstance.addBlock(new DeletedPart(nlps, (byte) 1, prevGlobAttySet), forward);

                     cleanDeleteEndOfPara = 2;
                }
                else
                {

                     deleteURInstance.addBlock(new DeletedPart(deleteString, prevGlobAttySet), forward);
                     deleteURInstance.addBlock(new DeletedPart(nlps, (byte) 1, prevGlobAttySet), forward);
                     cleanDeleteEndOfPara = 0;
                }     

           }
      }        

      private boolean lastDeletedIsPara = false;

      private void procCheckStyle(byte nlps, boolean forward, java.awt.event.KeyEvent ket)
      {    
           javax.swing.text.Element block, pe;

           int loc = this.getCaretPosition();

           if (nlps == 0)
           {

                deleteURInstance.addBlock(new DeletedPart(deleteString, prevGlobAttySet), forward);
                cleanDeleteEndOfPara = 0;
                lastDeletedIsPara = false;
           }
           else if (nlps == -6)
           {
                dealWithBR(nlps, forward, ket);
                lastDeletedIsPara = false;
           }
           else if (nlps == 6)
           {
                if (ket.getKeyCode() == KeyEvent.VK_BACK_SPACE) 
                {
                     pe = doc.getParagraphElement(loc - 1);

                     deleteBlankPara = (pe.getEndOffset() - pe.getStartOffset() == 1);
                     deleteURInstance.addBlock(new DeletedPart(deleteString, prevGlobAttySet), forward);
                     deleteURInstance.addBlock(new DeletedPart(nlps, (byte) 1, ppCurrentAS), forward);
                     block = doc.getCharacterElement(doc.getCharacterElement(loc - 1).getStartOffset() - 1);

                     if (isBR(block.getAttributes()))
                     {
                          cleanDeleteEndOfPara = 1;
                     }
                }
                else
                {

                     deleteURInstance.addBlock(new DeletedPart(deleteString, prevGlobAttySet), forward);

                     deleteURInstance.addBlock(new DeletedPart(nlps, (byte) 1, ppNextAS), forward);
                     cleanDeleteEndOfPara = 0;
                     lastDeletedIsPara = true;
                }
                setPNCParagraph(loc);
           }
      }

      private void checkStyle (java.awt.event.KeyEvent evt)
      {
           int loc = this.getCaretPosition();
           javax.swing.text.Element em = doc.getCharacterElement(loc);

           byte nlps = newLineParaString();
           if (em.getStartOffset() == loc) 
           {
               if (evt.getKeyCode() == KeyEvent.VK_BACK_SPACE) 

               {

                    if (deleteCode != 1)
                    {
                         procCheckStyle(nlps, false, evt);
                         deleteCode = 1;
                         deleteString = ;
                    }
                    else
                    {
                         if (attySetChanged)
                         { 
                              procCheckStyle(nlps, false, evt);
                              deleteString = ;
                         }
                    }
               }
               else if (evt.getKeyCode() == KeyEvent.VK_DELETE) 

               {
                    if (deleteCode != 2) 

                    {
                         procCheckStyle(nlps, true, evt);
                         deleteCode = 2;
                         deleteString = ;
                    }
                    else 
                    {

                         if (attySetChanged) 
                         {
                              procCheckStyle(nlps, true, evt);
                              deleteString = ;
                         }
                    }
               }
           }
           else 

           {
                if (deleteCode != 3)
                {

                    deleteURInstance.addBlock(new DeletedPart(deleteString, prevGlobAttySet), false); 
                    deleteCode = 3;
                    deleteString = ;
                }
           }
      }

      private void processDelete (java.awt.event.KeyEvent evt) 
              throws javax.swing.text.BadLocationException
      {
           String txt;

           setPNCParagraph(this.getCaretPosition());

           txt = getNextChar(evt); 

           checkStyle(evt);
           if (txt != null)
           {
                if (evt.getKeyCode() == KeyEvent.VK_DELETE)
                {
                    deleteString = deleteString + txt;
                }
                else if (evt.getKeyCode() == KeyEvent.VK_BACK_SPACE)
                {
                    deleteString = txt + deleteString;
                }
           }          
      }

      private String getNextChar(java.awt.event.KeyEvent evt) 
              throws javax.swing.text.BadLocationException
      {
           String txt;
           int start = this.getCaretPosition();

           if (evt.getKeyCode() == KeyEvent.VK_BACK_SPACE)
           {

                --start;
           }

           javax.swing.text.AttributeSet ass = doc.getCharacterElement(start).getAttributes();

           if (globAttySet != null)
           {

                attySetChanged = globAttySet != ass;
                if (attySetChanged)
                {
                     prevGlobAttySet = globAttySet;
                     globAttySet = ass;
                }    
           }
           else 
           {
                prevGlobAttySet = globAttySet = ass;

                attySetChanged = true;
           }

           txt = doc.getText(start, 1);
           deleteCaretLoc = start;
           return txt;
      }

      private void setPNCParagraph(int loc)
      {
           javax.swing.text.Element cpe = doc.getParagraphElement(loc), ppe, npe;
           npe = doc.getParagraphElement(cpe.getEndOffset());
           ppe = doc.getParagraphElement(cpe.getStartOffset() - 1);

           ppCurrentAS = cpe.getAttributes();

           if (isP_Implied(ppe.getAttributes()))
           {
                ppPrevAS = null;
           }
           else
           {
                ppPrevAS = ppe.getAttributes();
           }

           if (cpe == npe)
           {
                ppNextAS = null;
           }
           else
           {
                ppNextAS = npe.getAttributes();
           }
      }

      private void procTextEntry (int x) throws javax.swing.text.BadLocationException
      {   
          insertCaretPos = this.getSelectionStart() + x;

          if (!noSelection())
          {
               processDeleteSelection();
               recOp(DELETE_SELECTION, prevOp);
               deleteText(this.getSelectionStart(), this.getSelectionEnd());
          }
          recOp(TEXT_ENTRY, prevOp);
      }

      private void repositionCaret(int caretPos)
      {
           javax.swing.text.Element em = doc.getCharacterElement(caretPos);
           if (isPara(em.getAttributes()))
           {
                if (isBR(doc.getCharacterElement(em.getStartOffset() - 1).getAttributes()))
                {
                     switch (arrowDir)
                     {

                          case 2
                          case 4
                               if (em.getEndOffset() - 1 == doc.getLength())
                               {
                                    em = doc.getCharacterElement(em.getStartOffset() - 1);
                                    this.setCaretPosition(em.getStartOffset()); 
                               }
                               else
                               {
                                    this.setCaretPosition(em.getEndOffset());
                               }
                               break;
                          case 1
                          case 3
                               em = doc.getCharacterElement(em.getStartOffset() - 1); 
                               this.setCaretPosition(em.getStartOffset());
                               break;
                     }
                }
           }
      }

      private void resetSubSuperButtons ()
      {
           if (buttonSet)
           {
               try
               {
                    toolSuper.setSelected(isSuper);
                    toolSub.setSelected(isSub);
               }
               catch (java.lang.NullPointerException npe) {}
           }
      }

      private void resetSubSuper()
      {
           isSuper = isSub = false;
           resetSubSuperButtons();
      }

      private void resetBIUSbuttons()
      {
           if (buttonSet)
           {
               try
               {
                    toolB.setSelected(isBold);
                    toolI.setSelected(isItalic);
                    toolU.setSelected(isUnderlined);
                    toolS.setSelected(isStriked);
               }
               catch (java.lang.NullPointerException npe) {}
           }
      }

      private void resetStyleQ ()
      { 
           styleQLoc = -1;
           if (biusQ != null)
           {
               biusQ[0] = false;
               biusQ[1] = false;
               biusQ[2] = false;
               biusQ[3] = false;

           }
           fscQ[0] = null;
           fscQ[1] = null;
           fscQ[2] = null;
           subSuperQ = null;
      }

      private void resetBIUS()
      {
           isBold = false;
           isItalic = false;
           isUnderlined = false;
           isStriked = false;
           resetBIUSbuttons();
      }

      private void resetParaStyle()
      {
           isParaLeft = isParaRight = isParaCenter = isParaJustify = false;
           resetParaButtons();
      }

      private void resetParaButtons()
      {   
           if (buttonSet)
           {
               try
               {
                    toolParaL.setSelected(isParaLeft);
                    toolParaR.setSelected(isParaRight);
                    toolParaC.setSelected(isParaCenter);
                    toolParaJ.setSelected(isParaJustify);
               }
               catch (java.lang.NullPointerException npe){}
           }
      }

      private void resetFSC()
      {

           javax.swing.text.Element em = doc.getElement(doc.getDefaultRootElement(), 
                        javax.swing.text.Style.NameAttribute, javax.swing.text.html.HTML.Tag.BODY);

           dispFont = getCurrentFont(em);
           dispSize = getTextSize(em);

           prevFont = ;
           prevSize = ;
           prevColor = ;
           resetFSCdisplay();
      }      

      private void resetFSCdisplay()
      {
           if (buttonSet)
           {
                try
                {

                     _specT = true;
                     toolF.setSelectedIndex(0);
                     toolF.getModel().setSelectedItem(dispFont);
                     toolSize.setSelectedIndex(0);
                     toolSize.getModel().setSelectedItem(dispSize);
                     _specT = false;
                }
                catch (java.lang.NullPointerException npe) {}
           }
      }

      private void fontComboClicked()
      {
           try
           {
                Object f = toolF.getSelectedItem();
                setFont(f.toString());
           }
           catch (java.lang.NullPointerException npe){}
      }

      private void sizeComboClicked()
      {
           try
           {
                Object s = toolSize.getSelectedItem();
                setSize(s.toString());
           }
           catch (java.lang.NullPointerException npe){}
      }

      private void txtMouseClicked(java.awt.event.MouseEvent evt)
      {
           resetSelection(getSelectionStart(), getSelectionEnd(), forwardSelection());           
           searchRecord = null;
           dialogOpened = false;
           recOp(CARET_MOVE, prevOp);
      }

      private void txtMouseDragged(java.awt.event.MouseEvent evt)
      {
          recOp(CARET_MOVE, prevOp);
          searchRecord = null;
      }

      private void showQueuedStyle()
      {
           if(biusQ != null)
           {
               isBold = biusQ[0];
               isItalic = biusQ[1];
               isUnderlined = biusQ[2];
               isStriked = biusQ[3];
               resetBIUSbuttons();
           }

           resetFSC();           
           if (fscQ[0] != null)
           {
                dispFont = fscQ[0];
           }

           if (fscQ[1] != null)
           {
                dispSize = fscQ[1];
           }

           if (subSuperQ != null)
           {
               isSub = isSuper = false;
               isSub = subSuperQ.compareTo(sub) == 0;
               isSuper = subSuperQ.compareTo(super) == 0;
           }
           else
           {
                isSub = isSuper = false;
           }
           resetFSCdisplay();
           resetSubSuperButtons();
      }

      private void txtCaretUpdate(javax.swing.event.CaretEvent evt)
      {    
           if (!reflectStyle)
           {
                return;
           }
           reflectStyle();
      }

      private boolean paraEmpty(javax.swing.text.Element pe)
      {
           boolean isBr = true;
           javax.swing.text.Element em = doc.getCharacterElement(pe.getStartOffset());

           do
           {
                if (em.getAttributes().getAttribute(CR) != null)
                {
                     break;
                }
                else
                {
                     isBr = isBR(em.getAttributes());
                     em = doc.getCharacterElement(em.getEndOffset());
                }
           } while (isBr);
           return isBr;
      }

      private void reflectStyle()
      {                      
           int selStart = getSelectionStart();
           int selEnd = getSelectionEnd();

           if (selStart == selEnd)
           {
                repositionCaret(selStart);
                selStart = getSelectionStart();
                selEnd = getSelectionEnd();
           }

           if (arrowDir != 0)
           {
               recOp(CARET_MOVE, prevOp);
               arrowDir = 0;
           }

           if (outOfRange(selStart, selEnd))
           {
                return;
           }

           resetSubSuper();
           resetFSC();
           resetBIUS();
           resetParaStyle();

           if (styleQLoc = 0 && getCaretPosition() == styleQLoc)
           {
                showQueuedStyle();
                return;
           }

           javax.swing.text.Element em = doc.getCharacterElement(selStart);
           javax.swing.text.Element pe = doc.getParagraphElement(selEnd);

           if (selStart == selEnd) 
           {

               showParagraphStyle(em, false);

               if (selStart == em.getStartOffset())
               {

                    if (selStart == pe.getStartOffset())
                    {
                         if (!isFirstPara(pe))
                         {
                              if (paraEmpty(pe)) 
                              {
                                   em = doc.getCharacterElement(em.getStartOffset() - 1);
                                   em = getNonBR(em);
                                   if (!isBlockEditable(em))
                                   {
                                        em = null;
                                   }
                              }
                              else 
                              {
                                   if (isBR(em.getAttributes())) 
                                   {                                       
                                        em = getNonBRfw(em);
                                   }
                              }
                         }
                    }
                    else
                    {
                         em = doc.getCharacterElement(em.getStartOffset() - 1);
                         em = getNonBR(em);
                         if (!isBlockEditable(em))
                         {    
                              em = null;
                         }
                    }
               }
               else if (selStart == pe.getEndOffset() - 1)
               {
                    em = doc.getCharacterElement(em.getStartOffset() - 1);
                    em = getNonBR(em);
                    if (!isBlockEditable(em))
                    {
                         em = null;
                    }
               }

               if (em != null)
               {
                    showCurrentStyle(em, false);
                    getSubSuper(em, false);
                    getCurrentFont(em);
                    getCurrentColor(em);
                    getTextSize(em);
               }
           }
           else 
           {

                resetBIUS();
                resetSubSuper();
                resetParaStyle();

                resetFSC();

                em = doc.getCharacterElement(selStart);

                showCurrentStyle(em, true);
                getSubSuper(em, true);
                getCurrentFont(em);
                showParagraphStyle(em, true);
                getTextSize(em);
                getCurrentColor(em);
                int i = 0;

                while (em.getEndOffset()  selEnd)
                {
                    em = doc.getCharacterElement(em.getEndOffset());
                    showCurrentStyle(em, true);
                    getSubSuper(em, true);
                    getCurrentFont(em);
                    getTextSize(em);
                    getCurrentColor(em);

                    if (!pe.equals(doc.getParagraphElement(em.getStartOffset())))
                    {
                         resetParaStyle();
                    }

                    if (i++  doc.getLength())
                    {
                         break;
                    }
                }
           }
           prevFont = ;
           prevSize = ;
           prevColor = ;
           resetBIUSbuttons();
           resetParaButtons();
           resetFSCdisplay();
           resetSubSuperButtons();

           this.setCaretColor(Color.black);
      }

      private void txtComponentResized(java.awt.event.ComponentEvent evt)
      {
           int selStart = this.getSelectionStart();
           int selEnd = this.getSelectionEnd();
           boolean f = forwardSelection();
           detach();

           resetSelection(selStart, selEnd, f);
      }

      private void insertParagraphRightInTheMiddle(javax.swing.text.Element pe, int loc)
              throws javax.swing.text.BadLocationException, java.io.IOException
      {

           javax.swing.text.Element em = doc.getCharacterElement(loc), 
                   emP = doc.getCharacterElement(em.getStartOffset() - 1),
                   emF = doc.getCharacterElement(em.getEndOffset());
           String t1;

          if (isBR(em.getAttributes()) && isBR(emP.getAttributes()))
          {

               t1 = getPartHTML(em.getStartOffset(), pe.getEndOffset() - 1);
               doc.remove(emF.getStartOffset(), (pe.getEndOffset() - 1) - emF.getStartOffset());

          }

          else if (!isBR(em.getAttributes()) && isBR(emP.getAttributes()))
          {

               if (loc == em.getStartOffset())
               {
                   t1 = getPartHTML(em.getStartOffset(), pe.getEndOffset() - 1);
                   doc.remove(em.getStartOffset(), (pe.getEndOffset() - 1) - em.getStartOffset());
                   doc.insertBeforeEnd(pe, br);
               }
               else
               {
                    t1 = getPartHTML(loc, pe.getEndOffset() - 1);
                    doc.remove(loc, (pe.getEndOffset() - 1) - loc);
               }

          }

          else if (isBR(em.getAttributes()) && !isBR(emP.getAttributes()))
          {
                t1 = getPartHTML(em.getStartOffset(), pe.getEndOffset() - 1);
                doc.remove(em.getStartOffset(), (pe.getEndOffset() - 1) - em.getStartOffset());
          }
          else
          {
                t1 = getPartHTML(loc, pe.getEndOffset() - 1);
                doc.remove(loc, (pe.getEndOffset() - 1) - loc);
          }

          if (pe == doc.getParagraphElement(pe.getEndOffset()))
          {
               doc.insertBeforeEnd(pe.getParentElement(), t1);
          }
          else
          {
               doc.insertAfterEnd(pe, t1);
          }
      }

      private boolean skipDelete(java.awt.event.KeyEvent evt)
      {

           int loc = getCaretPosition();
           if (getSelectionStart() != getSelectionEnd()) return false; 

           javax.swing.text.Element block, pBlock;
           block = doc.getCharacterElement(loc);
           pBlock  = doc.getParagraphElement(loc);
           if (evt.getKeyCode() == KeyEvent.VK_DELETE)
           {
                if (loc = doc.getLength())
                {
                     return true;
                }
                else
                {
                     return isBR(block.getAttributes()) && 
                             block.getEndOffset() == pBlock.getEndOffset() - 1 &&
                             pBlock.getEndOffset() = doc.getLength();
                }

           }
           else
           {
                return isFirstPara(pBlock) && (loc == pBlock.getStartOffset());
           }          
      }

      private boolean checkDeleteAtStartOfPara()
      {
           return (doc.getParagraphElement(this.getCaretPosition()).getStartOffset() == this.getCaretPosition());
      }

      private void processEnter() throws javax.swing.text.BadLocationException, java.io.IOException
      {
           int loc = this.getCaretPosition();

           if (outOfRange(loc, loc))
           {
                resetUndoRedo();
                this.setCaretPosition(getStartLoc());
                return;
           }

           javax.swing.text.Element pe = doc.getParagraphElement(loc), em;
           em = doc.getCharacterElement(loc);
           String style = style=text-align + getParaStyle(pe.getAttributes()) + ;;

           if (getParagraphIndent(pe).length() == 0)
           {
                style = style + ;
           }
           else
           {
                style = style + margin-left + getParagraphIndent(pe) + ;;
           }

           if (pe.getStartOffset() == loc)
           {
                doc.insertBeforeStart(pe, p  + style + brp);

                pe = doc.getParagraphElement(loc);
                specInsertStart = pe.getStartOffset();

                pe = doc.getParagraphElement(pe.getEndOffset());
                insertCaretPos = pe.getStartOffset();
           }

           else if (pe.getEndOffset() == loc + 1)
           {

                if (pe == doc.getParagraphElement(pe.getEndOffset()))
                {

                    pe = pe.getParentElement();
                    doc.insertBeforeEnd(pe, p  + style + brp);
                }
                else
                {     
                    doc.insertAfterEnd(pe, p  + style + brp);
                }

                pe = doc.getParagraphElement(loc);
                specInsertStart = pe.getEndOffset() - 1;

                pe = doc.getParagraphElement(pe.getEndOffset());
                insertCaretPos = pe.getStartOffset();
          }
          else 
          {
               insertParagraphRightInTheMiddle(pe, loc);
               specInsertStart = insertCaretPos = loc;
               pe = doc.getParagraphElement(loc);
               pe = doc.getParagraphElement(pe.getEndOffset());
               insertCaretPos = pe.getStartOffset();
          }
          detach();
          this.setCaretPosition(insertCaretPos);
      }

      public void setQuickFormat(int accessNumber)
      {
           if (quickFormat == null) return;

           switch (accessNumber)
           {
                case 1
                     quickFormat[0] = null;
                     break;
                case 2
                     quickFormat[1] = null;
                     break;
                case 3
                     quickFormat[2] = null;
                     break;
                case 4
                     quickFormat[3] = null;
                     break;
                case 5
                     quickFormat[4] = null;
                     break;
                case 6
                     quickFormat[5] = null;
                     break;
                case 7
                     quickFormat[6] = null;
                     break;
                case 8
                     quickFormat[7] = null;
                     break;
                case 9
                     quickFormat[8] = null;
                     break;
           }
      }

      public void setQuickFormat
        (int accessNumber, boolean b, boolean i, boolean u, boolean s, String font, String size, String color, String align)
      {
           if (quickFormat == null)
           {
                quickFormat = new QuickFormatScheme[9];
           }

           QuickFormatScheme qf =
                   new QuickFormatScheme(b, i, u, s, font, size, color, align);

           switch (accessNumber)
           {
                case 1
                     quickFormat[0] = qf;
                     break;
                case 2
                     quickFormat[1] = qf;
                     break;
                case 3
                     quickFormat[2] = qf;
                     break;
                case 4
                     quickFormat[3] = qf;
                     break;
                case 5
                     quickFormat[4] = qf;
                     break;
                case 6
                     quickFormat[5] = qf;
                     break;
                case 7
                     quickFormat[6] = qf;
                     break;
                case 8
                     quickFormat[7] = qf;
                     break;
                case 9
                     quickFormat[8] = qf;
                     break;
           }
      }

      private void getQuickFormatScheme(KeyEvent kev)
      {
           qfs = null;
           if (quickFormat == null)
           {
                return;
           }

           switch (kev.getKeyCode())
           {
                case KeyEvent.VK_1
                     qfs = quickFormat[0];
                     break;
                case KeyEvent.VK_2
                     qfs = quickFormat[1];
                     break;
                case KeyEvent.VK_3
                     qfs = quickFormat[2];
                     break;
                case KeyEvent.VK_4
                     qfs = quickFormat[3];
                     break;
                case KeyEvent.VK_5
                     qfs = quickFormat[4];
                     break;
                case KeyEvent.VK_6
                     qfs = quickFormat[5];
                     break;
                case KeyEvent.VK_7
                     qfs = quickFormat[6];
                     break;
                case KeyEvent.VK_8
                     qfs = quickFormat[7];
                     break;
                case KeyEvent.VK_9
                     qfs = quickFormat[8];
                     break;
           }
      }

      boolean pSQ;
      QuickFormatScheme qfs;
      private void txtKeyPressed(java.awt.event.KeyEvent evt)
              throws javax.swing.text.BadLocationException
      {
           boolean ctrlPressed = evt.isControlDown();

           pSQ = !ctrlPressed;

           qfs = null;
           int selStart = this.getSelectionStart();
           int selEnd = this.getSelectionEnd();
           boolean forwardSelection = forwardSelection();

           if (evt.getKeyCode() == KeyEvent.VK_CONTEXT_MENU)
           {
               this.setComponentPopupMenu(getPopMenu());
           }

           switch (evt.getKeyCode())
           {
                case KeyEvent.VK_DELETE
                     pSQ = false;
                     if (outOfRange(getSelectionStart(), getSelectionEnd()))
                     {
                          resetUndoRedo();
                          evt.consume();
                          return;
                     }
                     if (skipDelete(evt))
                     {
                          evt.consume();
                     }
                     else
                     {
                          if (selStart == selEnd)
                          {
                               recOp(DELETE_DELETE, prevOp);
                               processDelete(evt);
                          }
                          else
                          {
                               processDeleteSelection();
                               recOp(DELETE_SELECTION, prevOp);
                          }    
                     }
                     break;
                case KeyEvent.VK_BACK_SPACE
                     pSQ = false;
                     if (outOfRange(getSelectionStart(), getSelectionEnd()))
                     {
                          resetUndoRedo();
                          evt.consume();
                          return;
                     }
                     if (skipDelete(evt))
                     {
                          evt.consume();
                     }
                     else
                     {
                          if (selStart == selEnd)
                          {
                               recOp(BSPACE_DELETE, prevOp);
                               processDelete(evt);
                          }
                          else
                          {
                               processDeleteSelection();
                               recOp(DELETE_SELECTION, prevOp);
                          }
                     }
                     break;

                case KeyEvent.VK_ENTER
                     pSQ = false;
                     evt.consume();
                     if (!evt.isShiftDown()) 
                     {
                         try
                         {
                              if (selStart != selEnd)
                              {
                                   processDeleteSelection();
                                   recOp(DELETE_SELECTION, prevOp); 
                                   deleteText(selStart, selEnd);
                              }
                              processEnter();
                              recOp(TEXT_ENTRY, prevOp);
                         }
                         catch (javax.swing.text.BadLocationException  java.io.IOException ee)
                         {
                              resetUndoRedo();
                         }
                     }
                     break;
                case KeyEvent.VK_ALT
                     pSQ = false;
                     evt.consume();
                     break;
                case KeyEvent.VK_TAB
                     evt.consume();
                     break;
                case KeyEvent.VK_RIGHT
                     pSQ = false;
                     reflectStyle = true;
                     arrowDir = 4;
                     break;
                case KeyEvent.VK_LEFT
                     pSQ = false;
                     reflectStyle = true;
                     arrowDir = 3;
                     break;
                case KeyEvent.VK_UP
                     pSQ = false;
                     reflectStyle = true;
                     arrowDir = 1;
                     break;
                case KeyEvent.VK_DOWN
                     pSQ = false;
                     reflectStyle = true;
                     arrowDir = 2;
                     break;
                case KeyEvent.VK_Z
                     if (ctrlPressed)
                     {
                          undo();
                          evt.consume();
                     }
                     break;
                case KeyEvent.VK_Y
                     if (ctrlPressed)
                     {
                          redo();
                          evt.consume();
                     }
                     break;
                case KeyEvent.VK_A
                     if (ctrlPressed)
                     {
                         evt.consume();
                         this.setSelectionStart(getStartLoc());
                         this.setSelectionEnd(getEndLoc());
                         recOp(CARET_MOVE, prevOp);
                     }
                     break;
                case KeyEvent.VK_B
                     if (ctrlPressed)
                     {
                         evt.consume();
                         setBold();
                     }
                     break;
                case KeyEvent.VK_I
                     if (ctrlPressed)
                     {
                         evt.consume();
                         setItalic();
                     }
                     break;
                case KeyEvent.VK_U
                     if (ctrlPressed)
                     {
                         evt.consume();
                         setUnderline();
                     }
                     break;
                case KeyEvent.VK_S
                     if (ctrlPressed)
                     {
                         evt.consume();
                         setStrikeThrough();
                     }
                     break;
                case KeyEvent.VK_K
                     if (ctrlPressed)
                     {
                         evt.consume();
                         setColor();
                     }
                     break;
                case KeyEvent.VK_L
                     if (ctrlPressed)
                     {
                         evt.consume();
                         setParaLeft();
                     }
                     break;
                case KeyEvent.VK_R
                     if (ctrlPressed)
                     {
                         evt.consume();
                         setParaRight();
                     }
                     break;
                case KeyEvent.VK_M
                     if (ctrlPressed)
                     {
                         evt.consume();
                         setParaCenter();
                     }
                     break;
                case KeyEvent.VK_J
                     if (ctrlPressed)
                     {
                         evt.consume();
                         setParaJustify();
                     }
                     break;
                case KeyEvent.VK_D
                     if (ctrlPressed)
                     {
                          pSQ = true;
                          evt.consume();
                          addStuff = 1;
                     }
                     break;
                case KeyEvent.VK_T
                     if (ctrlPressed)
                     {
                          pSQ = true;
                          evt.consume();
                          addStuff = 2;
                     }
                     break;
                case KeyEvent.VK_F
                     if (ctrlPressed)
                     {
                          evt.consume();
                          iSearch();
                     }
                     break;
                case KeyEvent.VK_1
                case KeyEvent.VK_2
                case KeyEvent.VK_3
                case KeyEvent.VK_4
                case KeyEvent.VK_5
                case KeyEvent.VK_6
                case KeyEvent.VK_7
                case KeyEvent.VK_8
                case KeyEvent.VK_9
                     if (ctrlPressed)
                     {
                          evt.consume();

                          getQuickFormatScheme(evt);
                          if (qfs != null)
                          {

                              if (selStart == selEnd)
                              {

                                   if (qfs.align != null)
                                   {
                                        if (formatParaOk(qfs.align))
                                        {
                                             if (prevOp == QUICK_FORMAT)                              
                                             {
                                                  prevOp = CARET_MOVE;
                                             }
                                             recOp(QUICK_FORMAT, prevOp);
                                        }
                                   }
                                   queueForQuickFormat(qfs, selStart);
                              }
                              else
                              {
                                   if (prevOp == QUICK_FORMAT)                              
                                   {
                                        prevOp = CARET_MOVE;
                                   }
                                   recOp(QUICK_FORMAT, prevOp);
                              }
                          }
                          resetSelection(selStart, selEnd, forwardSelection);
                     }
                     break;
           }
      }

      private void txtKeyReleased()
      {

           if (dialogOpened && arrowDir != 0)
           {
                reflectStyle();
           }
      }

      private void txtKeyTyped(java.awt.event.KeyEvent evt)
      {   
          int caretPos = this.getSelectionStart(), length = 0;

          String dateNtime;

          javax.swing.text.Element em = doc.getCharacterElement(caretPos), lem;

          int selStart = this.getSelectionStart();
          int selEnd = this.getSelectionEnd();

          lem = doc.getCharacterElement(selEnd);

          switch (evt.getKeyChar())
          {
               case KeyEvent.VK_ENTER
                    evt.consume();

                    if (evt.isShiftDown())
                    {

                         try
                         {
                              if (selStart != selEnd)
                              {
                                  processDeleteSelection();
                                  recOp(DELETE_SELECTION, prevOp);
                                  deleteText(selStart, selEnd);
                              }
                              recOp(TEXT_ENTRY, prevOp);
                              addNewLine(this.getSelectionStart()); 
                         }
                         catch (java.io.IOException  javax.swing.text.BadLocationException 
                                 java.lang.IllegalArgumentException ee)
                         {

                              resetUndoRedo();
                         }
                    }

                    break;
               case KeyEvent.VK_BACK_SPACE
               case KeyEvent.VK_DELETE

                    if (cleanDeleteEndOfPara == 1)
                    {
                         em = doc.getCharacterElement(caretPos - 1);
                         try
                         {
                              deleteText(em.getStartOffset(), em.getEndOffset());

                              deleteURInstance.addBlock(new DeletedPart(deleteString, prevGlobAttySet), false);
                              deleteURInstance.addBlock(new DeletedPart((byte) -6, (byte) 1, null), false);
                              deleteCaretLoc = em.getStartOffset();
                         }
                         catch (javax.swing.text.BadLocationException ble){}
                         cleanDeleteEndOfPara = 0;

                    }

                    else if (cleanDeleteEndOfPara == 2)
                    {
                         try
                         {
                              deleteText(em.getStartOffset(), em.getEndOffset());

                              deleteURInstance.addBlock(new DeletedPart(deleteString, prevGlobAttySet), true);
                              deleteURInstance.addBlock(new DeletedPart((byte) 6, (byte) 1, ppNextAS), true);
                              setPNCParagraph(caretPos);
                         }
                         catch (javax.swing.text.BadLocationException ble){}

                         lastDeletedIsPara = true;
                         cleanDeleteEndOfPara = 0;
                    }
                    else
                    {
                         deleteCaretLoc = caretPos;
                         cleanDeleteEndOfPara = 0;
                    }
                    break;
               case KeyEvent.VK_TAB
               default

                    if (!evt.isControlDown())
                    {

                         if (evt.getKeyChar() == KeyEvent.VK_TAB)
                         {
                              insertText( , null);
                         }
                         else
                         {
                              insertText(String.valueOf(evt.getKeyChar()), null);
                         }
                         evt.consume();

                         length = 1;
                         insertSetCaretPos(caretPos + length);
                    }
                    else
                    {
                         evt.consume();                         
                         if (addStuff == 1  addStuff == 2) 
                         {
                              if (addStuff == 1)
                              {
                                   dateNtime = sdf.format(new java.util.Date());
                              }
                              else
                              {
                                   dateNtime = stf.format(new java.util.Date());
                              }

                              insertText(dateNtime, null);

                              length = dateNtime.length();
                              addStuff = 0;
                              insertSetCaretPos(caretPos + length);
                         }
                    }
                    break;
          }

          if (pSQ)
          {
               boolean f = reflectStyle;
               processStyleQueue(length);
               insertSetCaretPos(caretPos + length);
               reflectStyle = f;
          }
          this.repaint();
      }

      private void insertSetCaretPos(int loc)
      {
           if (!outOfRange(loc, loc))
           {
                setCaretPosition(loc);
           }
           else
           {
                resetUndoRedo();
                setCaretPosition(getStartLoc());
           }
      }

      private void processStyleQueue(int l)
      {
           if ((this.getCaretPosition() - l) != styleQLoc  styleQLoc  0)
           {
                resetStyleQ();
                return;
           }

           boolean bius;
           if (styleQLoc = 0)
           {
                if (biusQ != null)
                {
                    bius = biusQ[0];
                    if (bius)
                    {
                         setStyle(new String[]{BOLD}, styleQLoc, styleQLoc + l);
                    }
                    else
                    {
                         setStyle(new String[]{NO_BOLD}, styleQLoc, styleQLoc + l);
                    }

                    bius = biusQ[1];
                    if (bius)
                    {
                         setStyle(new String[]{ITALIC}, styleQLoc, styleQLoc + l);
                    }
                    else
                    {
                         setStyle(new String[]{NO_ITALIC}, styleQLoc, styleQLoc + l);
                    }

                    bius = biusQ[2];
                    if (bius)
                    {
                         setStyle(new String[]{UNDERLINE}, styleQLoc, styleQLoc + l);
                    }
                    else
                    {
                         setStyle(new String[]{NO_UNDERLINE}, styleQLoc, styleQLoc + l);
                    }

                    bius = biusQ[3];
                    if (bius)
                    {
                         setStyle(new String[]{STRIKETHROUGH}, styleQLoc, styleQLoc + l);
                    }
                    else
                    {
                         setStyle(new String[]{NO_STRIKETHROUGH}, styleQLoc, styleQLoc + l);
                    }
                }

                if (fscQ[0] != null)
                {
                     setStyle(new String[]{FONT, fscQ[0]}, styleQLoc, styleQLoc + l);
                }

                if (fscQ[1] != null)
                {
                     setStyle(new String[]{SIZE, fscQ[1]}, styleQLoc, styleQLoc + l);
                }

                if (fscQ[2] != null)
                {
                     setStyle(new String[]{COLOR, fscQ[2]}, styleQLoc, styleQLoc + l);
                }

                if (subSuperQ != null)
                {
                     setStyle(new String[]{VERTICAL_ALIGN, subSuperQ}, styleQLoc, styleQLoc + l);
                }
           }
           resetStyleQ();
      }

      private void deleteText(int start, int end) throws BadLocationException
      {
           if (end  start) throw new BadLocationException(End less than start., 0);
           doc.remove(start, (end - start));
      }

      private byte getBIUSop (byte op)
      {
          byte i = 0;
          switch (op)
          {
               case SET_TEXT_STYLE_B
               case SET_TEXT_STYLE_NO_B
                         i = 0;
               break;
               case SET_TEXT_STYLE_I
               case SET_TEXT_STYLE_NO_I
                         i = 1;
               break;
               case SET_TEXT_STYLE_U
               case SET_TEXT_STYLE_NO_U
                         i = 2;
               break;
               case SET_TEXT_STYLE_S
               case SET_TEXT_STYLE_NO_S
                         i = 3;
               break;
          }
          return i;
      }

      private int[] styleTextCut(javax.swing.text.Element em, int start, int end)
      {

          if (start = em.getStartOffset() && end = em.getEndOffset()) 
          {

               return (new int[] {em.getStartOffset(), em.getEndOffset()});
          }
          else if (start  em.getStartOffset() && end  em.getEndOffset()) 
          {

               return (new int[] {start, end});
          }

          else if (start = em.getStartOffset() && end = em.getEndOffset()) 
          {

               return (new int[] {start, em.getEndOffset()});
          }

          else if (start = em.getStartOffset() && end = em.getEndOffset()) 
          {

               return (new int[] {em.getStartOffset(), end});
          }
          else
          {
               return null;
          }
      }

      private int[] testAndCut(javax.swing.text.Element em, int start, int end, boolean[] bius)
      {

           if (bius == null)
           {

                return styleTextCut(em, start, end);
           }

           boolean[] style = showCurrentStyle(em, true);

           if (bius[0] && style[0]) 
           {
                return styleTextCut(em, start, end);
           }
           else if(bius[1] && style[1]) 
           {
                return styleTextCut(em, start, end);
           }
           else if (bius[2] && style[2]) 
           {
                return styleTextCut(em, start, end);
           }
           else if (bius[3] && style[3]) 
           {
                return styleTextCut(em, start, end);
           }
           else
           {

                return null;
           }
      }

      private void poi2112(javax.swing.text.Element em, int startPos, int endPos, boolean[] BIUS, StyleUndoRedo sud)
      {
           int[] es;
           es = testAndCut(em, startPos, endPos, BIUS);
           if (es != null)
           {

                switch(sud.getOp())
                {
                     case SET_FONT

                          sud.addBlock(es, getCurrentFont(em));
                          break;
                     case SET_SIZE

                          sud.addBlock(es, getTextSize(em));
                          break;
                     case SET_TEXT_COLOR

                          sud.addBlock(es, getCurrentColor(em));
                          break;
                     case SET_SUPER
                     case SET_SUB
                     case SET_NO_SUPER
                     case SET_NO_SUB
                          sud.addBlock(es, getSubSuperTxt(em, true));
                          break;
                     default

                          sud.addBlock(es);
                          break;
                }
           }
      }

      private StyleUndoRedo BIUSplan (byte op)
      {
           int startPos = this.getSelectionStart();
           int endPos = this.getSelectionEnd();
           StyleUndoRedo styleObj = new StyleUndoRedo(new int[]{startPos, endPos}, op);
           return procBIUSplan(styleObj);
      }

      private StyleUndoRedo BIUSplan (byte op, String fsc)
      {
           int startPos = this.getSelectionStart();
           int endPos = this.getSelectionEnd();
           StyleUndoRedo styleObj = new StyleUndoRedo(new int[]{startPos, endPos}, op, fsc);
           return procBIUSplan(styleObj);      
      }

      private StyleUndoRedo procBIUSplan (StyleUndoRedo styleObj)
      {

         int startPos = styleObj.getStart();
         int endPos = styleObj.getEnd();
         int[] es;
         boolean[] BIUS = new boolean[]{false, false, false, false};
         byte op = styleObj.getOp();

         javax.swing.text.Element em = doc.getCharacterElement(startPos);

         BIUS[getBIUSop(op)] = true;

         if (op == SET_TEXT_STYLE_B  op == SET_TEXT_STYLE_I 
                 op == SET_TEXT_STYLE_U  op == SET_TEXT_STYLE_S)
         {
              es = testAndCut(em, startPos, endPos, BIUS);
              if(es != null)
              {
                   styleObj.addBlock(es); 
              }

              return styleObj;
         }

         if (op == SET_FONT  op == SET_TEXT_COLOR  op == SET_SIZE 
             op ==  SET_SUPER  op == SET_SUB  op == SET_NO_SUPER  op == SET_NO_SUB)
         {
               BIUS = null;
         }

         do
         {

              poi2112(em, startPos, endPos, BIUS, styleObj);

              em = doc.getCharacterElement(em.getEndOffset());

         } while (endPos  em.getEndOffset());

         if (em.getStartOffset()  endPos)
         {

              poi2112(em, startPos, endPos, BIUS, styleObj);
         }
         return styleObj;
      }

      private void switchDeleteSequence (byte opCode)

      {

           if (opCode == BSPACE_DELETE)
           {

           }
           else if (opCode == DELETE_DELETE)
           {

           }
      }

      private void urOpenTextEntry()
      {
          int i;
          if (specInsertStart  0)
          {
               i = this.getCaretPosition();
          }
          else
          {
               i = specInsertStart;
               specInsertStart = -666;
          }
          InsertUndoRedo o = new InsertUndoRedo(i);
          this.currentOp = o;
          urMgr.addOperation(o);
          reflectStyle = false;
      }

      private void urCloseTextEntry()
      {                    
          InsertUndoRedo o = (InsertUndoRedo) currentOp;
          o.setEnd(insertCaretPos);
          specInsertStart = -666;
          reflectStyle = true; 
      }

      private void openBIUS ()
      {

          StyleUndoRedo sud = BIUSplanInstance;
          urMgr.addOperation(sud);
      }

      private void recOp (byte currentCode, byte prevCode)
      {           
          if (currentCode != this.prevOp) 
          {               
               if (this.prevOp == CARET_MOVE) arrowDir = 0;

               this.prevOp = currentCode;

               switch (prevCode)
               {
                    case TEXT_ENTRY

                         urCloseTextEntry();
                         break;
                    case DELETE_DELETE
                         if (currentCode == BSPACE_DELETE)
                         {

                              switchDeleteSequence(BSPACE_DELETE);
                         }
                         else
                         {
                              if (lastDeletedIsPara)
                              {

                                   deleteURInstance.endDelete(new DeletedPart(, globAttySet),
                                      deleteCaretLoc);
                              }
                              else
                              {
                                   deleteURInstance.endDelete(new DeletedPart(deleteString, globAttySet),
                                      deleteCaretLoc);
                              }
                              lastDeletedIsPara = false;
                              deleteString = ;
                              globAttySet = null;
                              deleteCode = 0;

                         }

                         break;
                    case BSPACE_DELETE
                         if (currentCode == DELETE_DELETE)
                         {

                              switchDeleteSequence(BSPACE_DELETE);
                         }
                         else
                         {
                              deleteURInstance.endDelete(new DeletedPart(deleteString, globAttySet),
                                      deleteCaretLoc);
                              deleteString = ;
                              globAttySet = null;
                              deleteCode = 0;

                         }

                         break;
                     case UNDO
                         if (!(currentCode == REDO  currentCode == CARET_MOVE))
                         {

                              urMgr.resetInsertLocation();
                         }
                         else
                         {
                              this.prevOp = UNDO;
                         }
                         break;
                     case REDO
                         if (!(currentCode == UNDO  currentCode == CARET_MOVE))
                         {

                              urMgr.resetInsertLocation();
                         }
                         else
                         {
                              this.prevOp = REDO;
                         }
                         break;
               }

               switch (currentCode)
               {
                    case TEXT_ENTRY
                         urOpenTextEntry();
                         searchRecord = null;
                         break;
                    case DELETE_DELETE

                         if (prevCode == BSPACE_DELETE)
                         {

                              switchDeleteSequence(BSPACE_DELETE);
                         }
                         else
                         {
                              deleteURInstance = new DeleteUndoRedo();
                              urMgr.addOperation(deleteURInstance);

                         }
                         searchRecord = null;
                         break;
                    case BSPACE_DELETE

                         if (prevCode == DELETE_DELETE)
                         {

                              switchDeleteSequence(DELETE_DELETE);
                         }
                         else
                         {
                              deleteURInstance = new DeleteUndoRedo();
                              urMgr.addOperation(deleteURInstance);
                              if (checkDeleteAtStartOfPara())
                              {
                                   deleteURInstance.addBlock(new DeletedPart(, null), true); 
                              }

                         }
                         searchRecord = null;
                         break;
                    case SET_TEXT_STYLE_B
                    case SET_TEXT_STYLE_I
                    case SET_TEXT_STYLE_U
                    case SET_TEXT_STYLE_S
                    case SET_TEXT_STYLE_NO_B
                    case SET_TEXT_STYLE_NO_I
                    case SET_TEXT_STYLE_NO_U
                    case SET_TEXT_STYLE_NO_S
                    case SET_SUB
                    case SET_SUPER
                    case SET_NO_SUB
                    case SET_NO_SUPER
                    case SET_FONT
                    case SET_SIZE
                    case SET_TEXT_COLOR
                         openBIUS();
                         break;
                    case DELETE_SELECTION

                         urMgr.addOperation(bur);
                         searchRecord = null;

                         break;
                    case REPLACE
                         urMgr.addOperation(replaceUndoRedo);
                         break;
                    case SET_PARA_STYLE_L
                    case SET_PARA_STYLE_R
                    case SET_PARA_STYLE_C
                    case SET_PARA_STYLE_J
                         urMgr.addOperation(paraURInstance);
                         break;
                    case SET_PARA_INDENT
                         urMgr.addOperation(indentUndoRedo);
                         break;
                    case UNDO
                         urMgr.setUndoRedoPoint();
                         break;
                    case QUICK_FORMAT
                         urMgr.addOperation(new QuickFormatUR(getSelectionStart(), getSelectionEnd(), 
                                 forwardSelection(), qfs));
                         break;
               }
          }
      }

      private void centrizeMe(javax.swing.JDialog jdm)
      {
           int x, y;
           x = theJSP.getLocationOnScreen().x + (this.getWidth()  2);

           x = x - (jdm.getWidth()  2);

           jdm.setLocation(x, theJSP.getLocationOnScreen().y + 80);
      }

      private void iSearch()
      {
           if (searchDlg == null)
           {
                searchDlg = new Search();
                centrizeMe(searchDlg);
                searchDlg.setVisible(true);
           }
           else
           {
               centrizeMe(searchDlg);
               searchDlg.setVisible(true);
           }
           searchReplaceDlg = null;
      }

      private void iReplace()
      {
           if (searchReplaceDlg == null)
           {
                searchReplaceDlg = new SearchReplace();
                centrizeMe(searchReplaceDlg);
                searchReplaceDlg.setVisible(true);
           }
           else
           {
               centrizeMe(searchReplaceDlg);
               searchReplaceDlg.setVisible(true);
           }
           searchDlg = null;
      }

      public void search()
      {
           iSearch();
      }        

      public void replace()
      {
           iReplace();
      }

      private void processSingleDelete(DeleteUndoRedo dur) 
              throws java.io.IOException, javax.swing.text.BadLocationException
      {
           javax.swing.text.Element pe;
           String align, margin, paraStyle, text, style;
           int xyz = getEndLoc();

           pe = doc.getParagraphElement(dur.getStart());

           margin = getParagraphIndent(pe.getAttributes());
           align = getParaStyle(pe.getAttributes());
           if (margin.length()  0)
           {
                margin = margin-left + margin + ;;
           }

           if (align.length()  0)
           {
                align = text-align + align + ;;
           }

           paraStyle = margin + align;

           if (dur.getStart()  pe.getStartOffset())
           {
                text = getContBlocks(pe, pe.getStartOffset(), dur.getStart());
                text = p style= + paraStyle +  + text;
           }
           else
           {
                text = p style= + paraStyle + ;
           }

           int i = dur.size() - 1, loc;
           Object impissed;
           DeletedPart dp;

           loc = dur.getStart();           

           while (i = 0)
           {
               impissed = dur.get(i);

               if (impissed instanceof DeletedPart)
               {
                     dp = (DeletedPart) impissed;

                     if (dp.isNewLine())
                     {
                          text = text + br; 
                     }
                     else if (dp.isNewParagraph())
                     {                          
                          margin = getParagraphIndent(dp.getAttributes());
                          align = getParaStyle(dp.getAttributes());
                          if (margin.length()  0)
                          {
                               margin = margin-left + margin + ;;
                          }

                          if (align.length()  0)
                          {
                               align = text-align + align + ;;
                          }
                          paraStyle = margin + align;

                          text = text + pp style= + paraStyle + ;
                     }
                     else if (dp.isText() && !dp.skipThis())
                     {
                          style = getCurrentStyle(dp.getAttributes());
                          text = text + span style= + style +  + destroySpace(cleanText(dp.getText())) + span;
                     }  
               }
               i--;
           }

           text = text + getContBlocks(pe, dur.getStart(), pe.getEndOffset()) + p;

           doc.setOuterHTML(pe, text);
           loc = loc + (getEndLoc() - xyz);
           dur.setEnd(loc);
      }

      public void insertSpecialCharacter(int cCode)
      {
          int i = getCaretPosition() + 1;
          insertText(String.valueOf(Character.toChars(cCode)), null);
          insertSetCaretPos(i);
          processStyleQueue(1);
          insertSetCaretPos(i);
      }

      private class WordCount extends javax.swing.JDialog 
      {
          public WordCount(int[] lope)
          {
               super();
               initComponents();

               java.text.DecimalFormat jdf = new java.text.DecimalFormat();
               jdf.setGroupingSize(3);

               labelAllChar.setText(jdf.format(lope[0]));
               labelAllNoSpace.setText(jdf.format(lope[1]));
               labelAllWord.setText(jdf.format(lope[2]));

               labelSelChar.setText(jdf.format(lope[3]));
               labelSelNoSpace.setText(jdf.format(lope[4]));
               labelSelWord.setText(jdf.format(lope[5]));

          }

          private void initComponents() 
          {
               java.awt.GridBagConstraints gridBagConstraints;

               jPanel1 = new javax.swing.JPanel();
               jLabel9 = new javax.swing.JLabel();
               jLabel10 = new javax.swing.JLabel();
               jLabel11 = new javax.swing.JLabel();
               jLabel12 = new javax.swing.JLabel();
               jLabel13 = new javax.swing.JLabel();
               jLabel14 = new javax.swing.JLabel();
               jLabel15 = new javax.swing.JLabel();
               jLabel16 = new javax.swing.JLabel();
               filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(25, 10), 
                       new java.awt.Dimension(25, 10), new java.awt.Dimension(25, 32767));
               labelAllChar = new javax.swing.JLabel();
               labelAllNoSpace = new javax.swing.JLabel();
               labelAllWord = new javax.swing.JLabel();
               labelSelChar = new javax.swing.JLabel();
               labelSelNoSpace = new javax.swing.JLabel();
               labelSelWord = new javax.swing.JLabel();
               filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 20), 
                       new java.awt.Dimension(0, 20), new java.awt.Dimension(32767, 20));
               jButton1 = new javax.swing.JButton();
               filler3 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 10), 
                       new java.awt.Dimension(0, 10), new java.awt.Dimension(32767, 10));

               setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
               setTitle(Word count);
               setResizable(false);
               addWindowFocusListener(new java.awt.event.WindowFocusListener() 
               {
               @Override
               public void windowGainedFocus(java.awt.event.WindowEvent evt) {}
               @Override
               public void windowLostFocus(java.awt.event.WindowEvent evt) {                    
                    WordCount.this.dispose();}
               });

               jPanel1.setBorder(javax.swing.BorderFactory.createEmptyBorder(15, 15, 15, 15));
               jPanel1.setLayout(new java.awt.GridBagLayout());

               jLabel9.setText(Words);
               gridBagConstraints = new java.awt.GridBagConstraints();
               gridBagConstraints.gridx = 0;
               gridBagConstraints.gridy = 3;
               gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
               jPanel1.add(jLabel9, gridBagConstraints);

               jLabel10.setText(Characters INCLUDING spaces);
               gridBagConstraints = new java.awt.GridBagConstraints();
               gridBagConstraints.gridx = 0;
               gridBagConstraints.gridy = 6;
               gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
               jPanel1.add(jLabel10, gridBagConstraints);

               jLabel11.setText(Characters EXCLUDING spaces);
               gridBagConstraints = new java.awt.GridBagConstraints();
               gridBagConstraints.gridx = 0;
               gridBagConstraints.gridy = 2;
               gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
               jPanel1.add(jLabel11, gridBagConstraints);

               jLabel12.setText(Characters INCLUDING spaces);
               gridBagConstraints = new java.awt.GridBagConstraints();
               gridBagConstraints.gridx = 0;
               gridBagConstraints.gridy = 1;
               gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
               jPanel1.add(jLabel12, gridBagConstraints);

               jLabel13.setText(Words);
               gridBagConstraints = new java.awt.GridBagConstraints();
               gridBagConstraints.gridx = 0;
               gridBagConstraints.gridy = 8;
               gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
               jPanel1.add(jLabel13, gridBagConstraints);

               jLabel14.setText(Characters EXCLUDING spaces);
               gridBagConstraints = new java.awt.GridBagConstraints();
               gridBagConstraints.gridx = 0;
               gridBagConstraints.gridy = 7;
               gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
               jPanel1.add(jLabel14, gridBagConstraints);

               jLabel15.setFont(new java.awt.Font(sansserif, 1, 12)); 
               jLabel15.setText(SELECTION);
               gridBagConstraints = new java.awt.GridBagConstraints();
               gridBagConstraints.gridx = 0;
               gridBagConstraints.gridy = 5;
               gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
               jPanel1.add(jLabel15, gridBagConstraints);

               jLabel16.setFont(new java.awt.Font(sansserif, 1, 12)); 
               jLabel16.setText(ALL);
               gridBagConstraints = new java.awt.GridBagConstraints();
               gridBagConstraints.gridx = 0;
               gridBagConstraints.gridy = 0;
               gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
               jPanel1.add(jLabel16, gridBagConstraints);
               gridBagConstraints = new java.awt.GridBagConstraints();
               gridBagConstraints.gridx = 1;
               gridBagConstraints.gridy = 0;
               jPanel1.add(filler1, gridBagConstraints);

               labelAllChar.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
               labelAllChar.setText(0);
               labelAllChar.setIconTextGap(0);
               labelAllChar.setMaximumSize(new java.awt.Dimension(120, 16));
               labelAllChar.setMinimumSize(new java.awt.Dimension(120, 16));
               labelAllChar.setPreferredSize(new java.awt.Dimension(120, 16));
               gridBagConstraints = new java.awt.GridBagConstraints();
               gridBagConstraints.gridx = 2;
               gridBagConstraints.gridy = 1;
               jPanel1.add(labelAllChar, gridBagConstraints);

               labelAllNoSpace.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
               labelAllNoSpace.setText(0);
               labelAllNoSpace.setIconTextGap(0);
               labelAllNoSpace.setMaximumSize(new java.awt.Dimension(120, 16));
               labelAllNoSpace.setMinimumSize(new java.awt.Dimension(120, 16));
               labelAllNoSpace.setPreferredSize(new java.awt.Dimension(120, 16));
               gridBagConstraints = new java.awt.GridBagConstraints();
               gridBagConstraints.gridx = 2;
               gridBagConstraints.gridy = 2;
               jPanel1.add(labelAllNoSpace, gridBagConstraints);

               labelAllWord.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
               labelAllWord.setText(0);
               labelAllWord.setIconTextGap(0);
               labelAllWord.setMaximumSize(new java.awt.Dimension(120, 16));
               labelAllWord.setMinimumSize(new java.awt.Dimension(120, 16));
               labelAllWord.setPreferredSize(new java.awt.Dimension(120, 16));
               gridBagConstraints = new java.awt.GridBagConstraints();
               gridBagConstraints.gridx = 2;
               gridBagConstraints.gridy = 3;
               jPanel1.add(labelAllWord, gridBagConstraints);

               labelSelChar.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
               labelSelChar.setText(0);
               labelSelChar.setIconTextGap(0);
               labelSelChar.setMaximumSize(new java.awt.Dimension(120, 16));
               labelSelChar.setMinimumSize(new java.awt.Dimension(120, 16));
               labelSelChar.setPreferredSize(new java.awt.Dimension(120, 16));
               gridBagConstraints = new java.awt.GridBagConstraints();
               gridBagConstraints.gridx = 2;
               gridBagConstraints.gridy = 6;
               jPanel1.add(labelSelChar, gridBagConstraints);

               labelSelNoSpace.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
               labelSelNoSpace.setText(0);
               labelSelNoSpace.setIconTextGap(0);
               labelSelNoSpace.setMaximumSize(new java.awt.Dimension(120, 16));
               labelSelNoSpace.setMinimumSize(new java.awt.Dimension(120, 16));
               labelSelNoSpace.setPreferredSize(new java.awt.Dimension(120, 16));
               gridBagConstraints = new java.awt.GridBagConstraints();
               gridBagConstraints.gridx = 2;
               gridBagConstraints.gridy = 7;
               jPanel1.add(labelSelNoSpace, gridBagConstraints);

               labelSelWord.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
               labelSelWord.setText(0);
               labelSelWord.setIconTextGap(0);
               labelSelWord.setMaximumSize(new java.awt.Dimension(120, 16));
               labelSelWord.setMinimumSize(new java.awt.Dimension(120, 16));
               labelSelWord.setPreferredSize(new java.awt.Dimension(120, 16));
               gridBagConstraints = new java.awt.GridBagConstraints();
               gridBagConstraints.gridx = 2;
               gridBagConstraints.gridy = 8;
               jPanel1.add(labelSelWord, gridBagConstraints);
               gridBagConstraints = new java.awt.GridBagConstraints();
               gridBagConstraints.gridx = 0;
               gridBagConstraints.gridy = 4;
               jPanel1.add(filler2, gridBagConstraints);

               jButton1.setText(OK);
               jButton1.setMaximumSize(new java.awt.Dimension(60, 28));
               jButton1.setMinimumSize(new java.awt.Dimension(60, 28));
               jButton1.setPreferredSize(new java.awt.Dimension(60, 28));
               jButton1.addActionListener(new java.awt.event.ActionListener() {
                    @Override
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                         WordCount.this.dispose();
                    }
               });
               gridBagConstraints = new java.awt.GridBagConstraints();
               gridBagConstraints.gridx = 2;
               gridBagConstraints.gridy = 10;
               gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
               jPanel1.add(jButton1, gridBagConstraints);
               gridBagConstraints = new java.awt.GridBagConstraints();
               gridBagConstraints.gridx = 1;
               gridBagConstraints.gridy = 9;
               jPanel1.add(filler3, gridBagConstraints);

               getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

               pack();
          }

          private javax.swing.Box.Filler filler1;
          private javax.swing.Box.Filler filler2;
          private javax.swing.Box.Filler filler3;
          private javax.swing.JButton jButton1;
          private javax.swing.JLabel jLabel10;
          private javax.swing.JLabel jLabel11;
          private javax.swing.JLabel jLabel12;
          private javax.swing.JLabel jLabel13;
          private javax.swing.JLabel jLabel14;
          private javax.swing.JLabel jLabel15;
          private javax.swing.JLabel jLabel16;
          private javax.swing.JLabel jLabel9;
          private javax.swing.JPanel jPanel1;
          private javax.swing.JLabel labelAllChar;
          private javax.swing.JLabel labelAllNoSpace;
          private javax.swing.JLabel labelAllWord;
          private javax.swing.JLabel labelSelChar;
          private javax.swing.JLabel labelSelNoSpace;
          private javax.swing.JLabel labelSelWord;

     }

      private class HTMEnt
      {
          public final String code;

          public HTMEnt(String c)
          {
               code = c;
          }

          @Override
          public String toString()
          {
               return String.valueOf(Character.toChars(Integer.parseInt(code, 16)));
          }
      }

      private class SpecChar extends javax.swing.JDialog
      {
          public SpecChar() 
          {
               super();
               initComponents();
               initMyComp();
          }

          private void initMyComp()
          {
               charBox.addItem(new HTMEnt(00a9));
               charBox.addItem(new HTMEnt(00ae));
               charBox.addItem(new HTMEnt(00b0));
               charBox.addItem(new HTMEnt(00b1));
               charBox.addItem(new HTMEnt(00ba));
               charBox.addItem(new HTMEnt(00bc));
               charBox.addItem(new HTMEnt(00bd));
               charBox.addItem(new HTMEnt(00be));
               charBox.addItem(new HTMEnt(00d7));
               charBox.addItem(new HTMEnt(00f7));
               charBox.addItem(new HTMEnt(00f8));
               charBox.addItem(new HTMEnt(00d8));
               charBox.addItem(new HTMEnt(00c6));
               charBox.addItem(new HTMEnt(00ab));
               charBox.addItem(new HTMEnt(00bb));
               charBox.addItem(new HTMEnt(00a7));
               charBox.addItem(new HTMEnt(00a2));
               charBox.addItem(new HTMEnt(00a3));
               charBox.addItem(new HTMEnt(00a5));
               charBox.addItem(new HTMEnt(00a6));
               charBox.addItem(new HTMEnt(00a1));
          }

          private void initComponents() 
          {
               java.awt.GridBagConstraints gridBagConstraints;

               jPanel1 = new javax.swing.JPanel();
               charBox = new javax.swing.JComboBox();
               txtChar = new javax.swing.JTextField();
               buttonOK = new javax.swing.JButton();
               jLabel5 = new javax.swing.JLabel();

               setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
               setTitle(Insert character);
               setMaximumSize(new java.awt.Dimension(2147483647, 38));
               setResizable(false);
               addWindowFocusListener(new java.awt.event.WindowFocusListener()
               {
                    @Override
                    public void windowGainedFocus(java.awt.event.WindowEvent evt) {
                    }
                    @Override
                    public void windowLostFocus(java.awt.event.WindowEvent evt) {
                         SpecChar.this.dispose();
                    }
               });
               addFocusListener(new java.awt.event.FocusAdapter() {
                    @Override
                    public void focusLost(java.awt.event.FocusEvent evt) {
                         formFocusLost(evt);
                    }
               });
               getContentPane().setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

               jPanel1.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
               jPanel1.setLayout(new java.awt.GridBagLayout());

               charBox.setMaximumSize(new java.awt.Dimension(60, 28));
               charBox.setMinimumSize(new java.awt.Dimension(60, 28));
               charBox.setPreferredSize(new java.awt.Dimension(60, 28));
               charBox.addActionListener(new java.awt.event.ActionListener() {
                    @Override
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                         charBoxActionPerformed(evt);
                    }
               });
               jPanel1.add(charBox, new java.awt.GridBagConstraints());

               txtChar.setToolTipText(Edit to enter other character);
               txtChar.setMinimumSize(new java.awt.Dimension(150, 28));
               txtChar.setPreferredSize(new java.awt.Dimension(100, 28));
               gridBagConstraints = new java.awt.GridBagConstraints();
               gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
               gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
               jPanel1.add(txtChar, gridBagConstraints);

               buttonOK.setText(Insert);
               buttonOK.setMaximumSize(new java.awt.Dimension(60, 28));
               buttonOK.setMinimumSize(new java.awt.Dimension(60, 28));
               buttonOK.setPreferredSize(new java.awt.Dimension(60, 28));
               buttonOK.addActionListener(new java.awt.event.ActionListener() 
               {
                    @Override
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                         buttonOKActionPerformed(evt);
                    }
               });
               gridBagConstraints = new java.awt.GridBagConstraints();
               gridBagConstraints.gridx = 2;
               gridBagConstraints.gridy = 0;
               jPanel1.add(buttonOK, gridBagConstraints);

               jLabel5.setFont(new java.awt.Font(sansserif, 0, 10)); 
               jLabel5.setText(Edit UTF codepoint (hex) to insert other character);
               gridBagConstraints = new java.awt.GridBagConstraints();
               gridBagConstraints.gridx = 0;
               gridBagConstraints.gridy = 1;
               gridBagConstraints.gridwidth = 3;
               gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
               gridBagConstraints.insets = new java.awt.Insets(5, 1, 0, 0);
               jPanel1.add(jLabel5, gridBagConstraints);

               getContentPane().add(jPanel1);

               pack();
          }

          private void buttonOKActionPerformed(java.awt.event.ActionEvent evt) {                                         

               int i = getCaretPosition() + 1;
               String nm = 
                       String.valueOf(Character.toChars(Integer.parseInt(txtChar.getText(), 16)));
               insertText(nm, null);
               insertSetCaretPos(i);
               processStyleQueue(1);
               insertSetCaretPos(i);
          }                                        

          private void charBoxActionPerformed(java.awt.event.ActionEvent evt) {                                        

               HTMEnt e = (HTMEnt) charBox.getSelectedItem();
               txtChar.setText(e.code);
          }                                       

          private void formFocusLost(java.awt.event.FocusEvent evt) {                               

          }                                                               

          private javax.swing.JButton buttonOK;
          private javax.swing.JComboBoxHTMEnt charBox;
          private javax.swing.JLabel jLabel5;
          private javax.swing.JPanel jPanel1;
          private javax.swing.JTextField txtChar;

      }

      private class Search extends javax.swing.JDialog 
      {
          public Search() 
          {
              super(theFrame, true);
              initComponents();
         }

          private void initComponents() 
          {
              java.awt.GridBagConstraints gridBagConstraints;

              contPane = new javax.swing.JPanel();
              paneButtons = new javax.swing.JPanel();
              buttonPrev = new javax.swing.JButton();
              buttonNext = new javax.swing.JButton();
              filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 0), 
                      new java.awt.Dimension(5, 32767));
              paneCheckBoxes = new javax.swing.JPanel();
              checkWhole = new javax.swing.JCheckBox();
              checkMatchCase = new javax.swing.JCheckBox();
              filler3 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 5), new java.awt.Dimension(0, 5), 
                      new java.awt.Dimension(32767, 5));
              searchLabel = new javax.swing.JLabel();
              txtSearchText = new javax.swing.JTextField();
              labelResult = new javax.swing.JLabel();

              setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

              addWindowListener(new java.awt.event.WindowAdapter() {
                         @Override
                         public void windowClosing(java.awt.event.WindowEvent e) {
                              searchRecord = null;
                              labelResult.setText( );
                         }
                    });

              txtSearchText.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() 
              {
                    @Override
                    public void insertUpdate(DocumentEvent e) {

                         labelResult.setText( );
                         searchRecord = null; 
                    }

                    @Override
                    public void removeUpdate(DocumentEvent e) {

                         labelResult.setText( );
                         searchRecord = null;
                    }

                    @Override
                    public void changedUpdate(DocumentEvent e) {}
               });

              setTitle(Search ...);
              setResizable(false);
              getContentPane().setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

              contPane.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
              contPane.setLayout(new java.awt.GridBagLayout());

              paneButtons.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
              paneButtons.setAlignmentY(0.0F);
              paneButtons.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 0, 5));

              buttonPrev.setText(Previous);
              buttonPrev.setAlignmentY(0.0F);
              buttonPrev.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
              buttonPrev.setIconTextGap(0);
              buttonPrev.addActionListener(new java.awt.event.ActionListener() 
              {
                   String txtLabel;
                   @Override
                   public void actionPerformed(java.awt.event.ActionEvent evt) {
                        searchPrevious(txtSearchText.getText(), checkWhole.isSelected(), 
                                checkMatchCase.isSelected(), 
                                getSelectionStart(), getSelectionEnd(), false);
                        if (searchRecord != null)
                        {
                             txtLabel = searchRecord.foundLocation.length +  match(es);
                        }
                        else
                        {
                             txtLabel = No match; 
                        }
                        labelResult.setText(txtLabel);
                   }
              });
              paneButtons.add(buttonPrev);

              buttonNext.setText(Next);
              buttonNext.setIconTextGap(0);
              buttonNext.addActionListener(new java.awt.event.ActionListener() 
              {
                   String txtLabel;
                   @Override
                   public void actionPerformed(java.awt.event.ActionEvent evt) {
                        searchNext(txtSearchText.getText(), checkWhole.isSelected(), 
                                checkMatchCase.isSelected(), 
                                getSelectionStart(), getSelectionEnd(), false);
                        if (searchRecord != null)
                        {
                             txtLabel = searchRecord.foundLocation.length +  match(es);
                        }
                        else
                        {
                             txtLabel = No match; 
                        }
                        labelResult.setText(txtLabel);
                   }
              });
              paneButtons.add(buttonNext);
              paneButtons.add(filler2);

              gridBagConstraints = new java.awt.GridBagConstraints();
              gridBagConstraints.gridx = 1;
              gridBagConstraints.gridy = 2;
              gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
              contPane.add(paneButtons, gridBagConstraints);

              paneCheckBoxes.setLayout(new javax.swing.BoxLayout(paneCheckBoxes, javax.swing.BoxLayout.PAGE_AXIS));

              checkWhole.setText(Whole word);
              paneCheckBoxes.add(checkWhole);

              checkMatchCase.setText(Match case);

              paneCheckBoxes.add(checkMatchCase);
              paneCheckBoxes.add(filler3);

              checkWhole.addActionListener(new java.awt.event.ActionListener() {

                   @Override
                   public void actionPerformed(ActionEvent e) {
                        searchRecord = null;
                        labelResult.setText( );
                   }
              });
              checkMatchCase.addActionListener(new java.awt.event.ActionListener() {

                   @Override
                   public void actionPerformed(ActionEvent e) {
                        searchRecord = null;
                        labelResult.setText( );
                   }
              });

              gridBagConstraints = new java.awt.GridBagConstraints();
              gridBagConstraints.gridx = 1;
              gridBagConstraints.gridy = 1;
              gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
              contPane.add(paneCheckBoxes, gridBagConstraints);

              searchLabel.setText(Text to search);
              gridBagConstraints = new java.awt.GridBagConstraints();
              gridBagConstraints.gridx = 0;
              gridBagConstraints.gridy = 0;
              gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
              gridBagConstraints.insets = new java.awt.Insets(6, 0, 0, 10);
              contPane.add(searchLabel, gridBagConstraints);

              txtSearchText.setColumns(20);
              txtSearchText.setAlignmentX(0.0F);
              txtSearchText.setAlignmentY(0.0F);
              gridBagConstraints = new java.awt.GridBagConstraints();
              gridBagConstraints.gridx = 1;
              gridBagConstraints.gridy = 0;
              gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
              contPane.add(txtSearchText, gridBagConstraints);

              labelResult.setText( );
              gridBagConstraints = new java.awt.GridBagConstraints();
              gridBagConstraints.gridx = 0;
              gridBagConstraints.gridy = 3;
              gridBagConstraints.gridwidth = 2;
              gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
              gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 0);
              contPane.add(labelResult, gridBagConstraints);

              getContentPane().add(contPane);

              pack();
         } 

          private javax.swing.JButton buttonNext;
          private javax.swing.JButton buttonPrev;
          private javax.swing.JCheckBox checkMatchCase;
          private javax.swing.JCheckBox checkWhole;
          private javax.swing.JPanel contPane;
          private javax.swing.Box.Filler filler2;
          private javax.swing.Box.Filler filler3;
          private javax.swing.JLabel labelResult;
          private javax.swing.JPanel paneButtons;
          private javax.swing.JPanel paneCheckBoxes;
          private javax.swing.JLabel searchLabel;
          private javax.swing.JTextField txtSearchText;

      }

      private class SearchReplace extends javax.swing.JDialog 
      {
          public SearchReplace() 
          {
               super(theFrame, true);
               initComponents();
          }

          private void initComponents() 
          {
               java.awt.GridBagConstraints gridBagConstraints;

               contPane = new javax.swing.JPanel();
               paneButtons = new javax.swing.JPanel();
               buttonPrev = new javax.swing.JButton();
               buttonNext = new javax.swing.JButton();
               buttonReplaceCurrent = new javax.swing.JButton();
               buttonReplaceAll = new javax.swing.JButton();
               paneCheckBoxes = new javax.swing.JPanel();
               checkWhole = new javax.swing.JCheckBox();
               checkMatchCase = new javax.swing.JCheckBox();
               filler3 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 5), new java.awt.Dimension(0, 5), new java.awt.Dimension(32767, 5));
               searchLabel = new javax.swing.JLabel();
               txtSearchText = new javax.swing.JTextField();
               jLabel1 = new javax.swing.JLabel();
               txtReplacementText = new javax.swing.JTextField();
               labelResult = new javax.swing.JLabel();

               setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

               addWindowListener(new java.awt.event.WindowAdapter() 
               {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) 
                    {
                         searchRecord = null;
                         labelResult.setText( );
                    }
                    });

               txtSearchText.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() 
               {
                    @Override
                    public void insertUpdate(DocumentEvent e) {
                         searchRecord = null;
                         labelResult.setText( );
                    }

                    @Override
                    public void removeUpdate(DocumentEvent e) {
                         searchRecord = null;
                         labelResult.setText( );
                    }

                    @Override
                    public void changedUpdate(DocumentEvent e) {}
               });

               txtReplacementText.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() 
               {
                    @Override
                    public void insertUpdate(DocumentEvent e) {
                         searchRecord = null;
                         labelResult.setText( );
                    }

                    @Override
                    public void removeUpdate(DocumentEvent e) {
                         searchRecord = null;
                         labelResult.setText( );
                    }

                    @Override
                    public void changedUpdate(DocumentEvent e) {}
               });

               setTitle(Search and replace ...);
               setResizable(false);

               getContentPane().setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

               contPane.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
               contPane.setLayout(new java.awt.GridBagLayout());

               paneButtons.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
               paneButtons.setAlignmentY(0.0F);
               paneButtons.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 0, 5));

               buttonPrev.setText(Previous);
               buttonPrev.setToolTipText(Replace next occurance);
               buttonPrev.setAlignmentY(0.0F);
               buttonPrev.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
               buttonPrev.setIconTextGap(0);
               buttonPrev.addActionListener(new java.awt.event.ActionListener() 
               {
                    @Override
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                         searchPrevious(txtSearchText.getText(), checkWhole.isSelected(), 
                                checkMatchCase.isSelected(), 
                                getSelectionStart(), getSelectionEnd(), false);   
                    }
               });
               paneButtons.add(buttonPrev);

               buttonNext.setText(Next);
               buttonNext.setToolTipText(Replace all occurences);
               buttonNext.setIconTextGap(0);
               buttonNext.addActionListener(new java.awt.event.ActionListener() 
               {
                    @Override
                    public void actionPerformed(java.awt.event.ActionEvent evt) {

                         searchNext(txtSearchText.getText(), checkWhole.isSelected(), 
                                checkMatchCase.isSelected(), 
                                getSelectionEnd(), getEndLoc(), false);   
                    }
               });
               paneButtons.add(buttonNext);

               buttonReplaceCurrent.setText(Replace current);
               buttonReplaceCurrent.addActionListener(new java.awt.event.ActionListener() 
               {
                    @Override
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        if (txtReplacementText.getText().length()  0)
                        {
                             replaceCurrent(txtReplacementText.getText());
                        }
                    }
               });
               paneButtons.add(buttonReplaceCurrent);

               buttonReplaceAll.setText(Replace all);
               buttonReplaceAll.addActionListener(new java.awt.event.ActionListener() 
               {
                    @Override
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                         if (txtReplacementText.getText().length() == 0) return;

                         int i = replaceAll(txtSearchText.getText(), checkWhole.isSelected(), 
                                checkMatchCase.isSelected(), txtReplacementText.getText(),
                                getSelectionStart(), getSelectionEnd());
                         labelResult.setText(Working....);

                         if (i = 0)
                         {
                              labelResult.setText(i +   occurrence(s) replaced.);
                         }
                         else
                         {
                              labelResult.setText(An error occured.);
                         }

                    }
               });
               paneButtons.add(buttonReplaceAll);

               gridBagConstraints = new java.awt.GridBagConstraints();
               gridBagConstraints.gridx = 0;
               gridBagConstraints.gridy = 3;
               gridBagConstraints.gridwidth = 2;
               gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 0);
               contPane.add(paneButtons, gridBagConstraints);

               paneCheckBoxes.setLayout(new javax.swing.BoxLayout(paneCheckBoxes, javax.swing.BoxLayout.PAGE_AXIS));

               checkWhole.setText(Whole word);
               paneCheckBoxes.add(checkWhole);

               checkMatchCase.setText(Match case);
               paneCheckBoxes.add(checkMatchCase);
               paneCheckBoxes.add(filler3);

               checkWhole.addActionListener(new java.awt.event.ActionListener() {

                   @Override
                   public void actionPerformed(ActionEvent e) {
                        searchRecord = null;
                        labelResult.setText( );
                   }
              });
               checkMatchCase.addActionListener(new java.awt.event.ActionListener() {

                   @Override
                   public void actionPerformed(ActionEvent e) {
                        searchRecord = null;
                        labelResult.setText( );
                   }
              });

               gridBagConstraints = new java.awt.GridBagConstraints();
               gridBagConstraints.gridx = 1;
               gridBagConstraints.gridy = 2;
               gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
               contPane.add(paneCheckBoxes, gridBagConstraints);

               searchLabel.setText(Text to search);
               gridBagConstraints = new java.awt.GridBagConstraints();
               gridBagConstraints.gridx = 0;
               gridBagConstraints.gridy = 0;
               gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
               gridBagConstraints.insets = new java.awt.Insets(6, 0, 0, 10);
               contPane.add(searchLabel, gridBagConstraints);

               txtSearchText.setColumns(20);
               txtSearchText.setAlignmentX(0.0F);
               txtSearchText.setAlignmentY(0.0F);
               gridBagConstraints = new java.awt.GridBagConstraints();
               gridBagConstraints.gridx = 1;
               gridBagConstraints.gridy = 0;
               gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
               contPane.add(txtSearchText, gridBagConstraints);

               jLabel1.setText(Replacement text);
               gridBagConstraints = new java.awt.GridBagConstraints();
               gridBagConstraints.gridx = 0;
               gridBagConstraints.gridy = 1;
               gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 10);
               contPane.add(jLabel1, gridBagConstraints);

               txtReplacementText.setColumns(20);
               txtReplacementText.setAlignmentX(0.0F);
               txtReplacementText.setAlignmentY(0.0F);
               gridBagConstraints = new java.awt.GridBagConstraints();
               gridBagConstraints.gridx = 1;
               gridBagConstraints.gridy = 1;
               gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
               contPane.add(txtReplacementText, gridBagConstraints);
               txtReplacementText.addFocusListener(new java.awt.event.FocusListener() {
                    @Override
                    public void focusGained(FocusEvent e) {
                         int l = txtReplacementText.getText().length();
                         txtReplacementText.setSelectionStart(0);
                         txtReplacementText.setSelectionEnd(l);
                    }

                    @Override
                    public void focusLost(FocusEvent e) {
                         txtReplacementText.setSelectionStart(0);
                         txtReplacementText.setSelectionEnd(0);
                    }
               });

               labelResult.setText( );
               gridBagConstraints = new java.awt.GridBagConstraints();
               gridBagConstraints.gridx = 0;
               gridBagConstraints.gridy = 4;
               gridBagConstraints.gridwidth = 2;
               gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
               gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 0);
               contPane.add(labelResult, gridBagConstraints);

               getContentPane().add(contPane);

               pack();
          }

          private javax.swing.JButton buttonNext;
          private javax.swing.JButton buttonPrev;
          private javax.swing.JButton buttonReplaceAll;
          private javax.swing.JButton buttonReplaceCurrent;
          private javax.swing.JCheckBox checkMatchCase;
          private javax.swing.JCheckBox checkWhole;
          private javax.swing.JPanel contPane;
          private javax.swing.Box.Filler filler3;
          private javax.swing.JLabel jLabel1;
          private javax.swing.JLabel labelResult;
          private javax.swing.JPanel paneButtons;
          private javax.swing.JPanel paneCheckBoxes;
          private javax.swing.JLabel searchLabel;
          private javax.swing.JTextField txtReplacementText;
          private javax.swing.JTextField txtSearchText;

      }

      private class InsertUndoRedo implements UndoRedoObject
      {
           public InsertUndoRedo(int start)
           {
                this.start = start; 
                this.end = start;
           }

           public void setEnd(int end)
           {
                this.end = end;
           }

           public void setStart(int start)
           {
                this.start = start;
           }

           @Override
           public int getStart() 
           {return start;}

           @Override
           public int getEnd() 
           {return end;}

           @Override
           public void undo() throws javax.swing.text.BadLocationException
           {

                if (text2Restore == null)
                {

                    processDeleteSelection(start, end);
                    text2Restore = bur;
                }
                deleteText(start, end);
           }

           @Override
           public void redo() throws javax.swing.text.BadLocationException, java.io.IOException
           {
                if (text2Restore != null)
                {
                     text2Restore.undo();
                }
           }

           @Override
           public byte getType()
           {
                return UndoRedoObject.INSERT;
           }

           private int start;
           private int end;
           private DeleteSelUndoRedo text2Restore;
      }

      private class DeleteSelUndoRedo implements UndoRedoObject
      {
           public DeleteSelUndoRedo(int s, int e, String t)
           {
                text = t;
                start = s;
                end = e;
           }

           @Override
           public void undo() throws javax.swing.text.BadLocationException, java.io.IOException
           {
                javax.swing.text.Element pe = doc.getParagraphElement(start);

                doc.setOuterHTML(pe, text);
           }

           @Override
           public void redo() throws javax.swing.text.BadLocationException
           {
                deleteText(start, end);
           }

           @Override
           public byte getType()
           {
                return UndoRedoObject.DELETE_SELECTION;
           }    

           public final String text;
           public final int start, end;

          @Override
          public int getStart() {
               return start;
          }

          @Override
          public int getEnd() {
               return end;
          }
      }

      private class DeleteUndoRedo extends java.util.LinkedListObject implements UndoRedoObject
      {
         private int currentBlocLoc = 0; 
         public int start = 0, end = 0;
         private boolean nlpJustAdded = false;
         private int length = 0;
         private byte brMode = 0;

         public int getStart()
         {
              return start;
         }

         public int getEnd()
         {
              return end;
         }

         public void setEnd(int i)
         {
              end = i;
         }

         public void setStart(int i)
         {
              start = i;
         }

         public void addBlock (DeletedPart dp, boolean forward)
         {               
               if (this.size()  0)
               {
                    if (forward) 
                    {

                         if (dp.isNewLine()  dp.isNewParagraph())
                         {
                              this.add(currentBlocLoc, dp);
                              currentBlocLoc = 0;
                              nlpJustAdded = true;
                              length++;
                         }

                         else
                         {
                              if (!dp.getText().equals(n))
                              {
                                   if (nlpJustAdded && dp.getText().equals( ))
                                   {
                                        dp.setSkipThis();
                                   }
                                   this.add(currentBlocLoc, dp);
                                   length = length + dp.getText().length();
                                   nlpJustAdded = false;
                              }
                              currentBlocLoc = 0;
                         } 
                    }
                    else 
                    {
                         this.addLast();
                         if (dp.isNewLine()  dp.isNewParagraph())
                         {
                              if (dp.isNewParagraph())

                              {
                                   this.add(currentBlocLoc, dp);
                                   currentBlocLoc = this.size() - 1;
                                   nlpJustAdded = true;
                                   brMode = 0;
                                   length++;
                              }
                              else

                              {
                                   if (brMode == 0)

                                   {
                                        this.add(currentBlocLoc, dp);
                                        currentBlocLoc = this.size() - 1;
                                        nlpJustAdded = true;
                                        length++;
                                   }
                                   else if (brMode == 1)
                                   {

                                        this.add(currentBlocLoc, dp);
                                        length++;
                                        currentBlocLoc = this.size() - 1;
                                        nlpJustAdded = true;
                                        brMode = 2;
                                   }
                                   else
                                   {

                                        brMode = 0;                                        
                                   }
                              }
                         }
                         else
                         {
                              if (!dp.getText().equals(n))
                              {
                                   if (nlpJustAdded && dp.getText().equals( ))
                                   {
                                        dp.setSkipThis();
                                   }
                                   this.add(currentBlocLoc, dp);
                                   length = length + dp.getText().length();
                                   nlpJustAdded = false;
                              }
                              currentBlocLoc = this.size() - 1;
                              brMode = 0;
                         }
                    }
              }
              else
              {
                   this.addFirst();
              }
         }

         public void endDelete(DeletedPart d, int e)
         {
              if (!d.getText().equals(n))
              {
                    if (nlpJustAdded && d.getText().equals( ))
                    {
                         d.setSkipThis();
                    }
                    this.add(currentBlocLoc, d);
              }
              start = e; 
         }

         public void endDelete (int e)
         {
              start = e;
              end = start + length;
         }

         @Override
         public void undo() throws java.io.IOException, javax.swing.text.BadLocationException
         {
              processSingleDelete(this);
         }

         @Override
         public void redo() throws javax.swing.text.BadLocationException 
         {
              deleteText(start, end);
         }

         @Override
         public byte getType()
           {
                return UndoRedoObject.DELETE;
           }
      }

      private class ReplaceUndoRedo implements UndoRedoObject
      {           
           private int beforeEnd, afterEnd;
           private final int startLocation;

           private DeleteSelUndoRedo before, after;

           public ReplaceUndoRedo(int sl)
           {
                startLocation = sl;
           }

           public void addBeforeState(DeleteSelUndoRedo b, int end)
           {
                beforeEnd = end;
                before = b;
           }

           public void addAfterState(DeleteSelUndoRedo a, int end)
           {
                afterEnd = end;
                after = a;
           }

           @Override
           public void undo() throws javax.swing.text.BadLocationException, java.io.IOException
           {
                 deleteText(startLocation, afterEnd); 
                 before.undo();
           }

           @Override
           public void redo() throws javax.swing.text.BadLocationException, java.io.IOException
           {
                 deleteText(startLocation, beforeEnd); 
                 after.undo();
           }

           @Override
           public byte getType()
           {
                return UndoRedoObject.REPLACE;
           }

          @Override
          public int getStart() 
          {
               return startLocation;
          }

          @Override
          public int getEnd() 
          {
               return startLocation;
          }
      }

      private class StyleUndoRedo implements UndoRedoObject
      {

          public StyleUndoRedo(int[] s, byte op)
          {
               sE = s;
               this.op = op;
          }

          public StyleUndoRedo(int[] s, byte op, String f)
          {
               sE = s;
               this.op = op;
               fsc = f;
          }

          public byte getOp ()
          {
               return op;
          }

          public int getStart()
          {
               return sE[0];
          }

          public int getEnd()
          {
               return sE[1];
          }

          private void makeSureTheArrayIsBigEnoughForThis()
          {
              if (blockIndex = histBlocks.length) 
              {
                    String[] newArray = new String[histBlocks.length + 10];
                    int i = 0;
                    while(i  histBlocks.length)
                    {
                         newArray[i] = histBlocks[i];
                         i++;
                    }
                    histBlocks = newArray;
              }
          }

          public void addBlock(int[] sE)
          {
               makeSureTheArrayIsBigEnoughForThis();

               histBlocks[blockIndex++] = String.valueOf(sE[0]) + %+ String.valueOf(sE[1]);
          }

          public void addBlock(int[] sE, String fsc)
          {
               makeSureTheArrayIsBigEnoughForThis();
               fsc = fsc.replace('%', '_');

               histBlocks[blockIndex++] = String.valueOf(sE[0]) + %+ String.valueOf(sE[1]
                       + % + fsc);
          }

          private void undoSubSuper()
          {
               String vals[];
               int i = 0;
               while (i  blockIndex)
               {
                    vals = histBlocks[i].split(%);
                    if (vals.length == 3)
                    {
                         setStyle(new String[]
                         {VERTICAL_ALIGN, vals[2]}, Integer.parseInt(vals[0]), Integer.parseInt(vals[1]));
                    }
                    else
                    {
                         setStyle(new String[]
                         {VERTICAL_ALIGN, }, Integer.parseInt(vals[0]), Integer.parseInt(vals[1]));
                    }
                    i++;
               }
          }

          private void undoFSC(byte op)
          {
               String vals[];
               int i = 0;
               while (i  blockIndex)
               {
                    vals = histBlocks[i].split(%);
                    switch (op)
                    {
                         case SET_TEXT_COLOR
                              setStyle(new String[]{COLOR, vals[2]}, Integer.parseInt(vals[0]), Integer.parseInt(vals[1]));
                              break;
                         case SET_FONT
                              setStyle(new String[]{FONT, vals[2]}, Integer.parseInt(vals[0]), Integer.parseInt(vals[1]));
                              break;
                         case SET_SIZE
                              setStyle(new String[]{SIZE, vals[2]}, Integer.parseInt(vals[0]), Integer.parseInt(vals[1]));
                              break;
                    }
                    i++;
               }
          }

          private void restyle (String s)
          {
               int i = 0;

               while (i  blockIndex)
               {
                    if (histBlocks[i].split(%).length == 2)
                    {
                         setStyle(new String[]{s},
                                 Integer.parseInt(histBlocks[i].split(%)[0]),
                                 Integer.parseInt(histBlocks[i].split(%)[1]));
                    }
                    i++;
               }
          }

          @Override
          public void undo()
          {
               switch (op)
               {

                    case SET_TEXT_STYLE_B
                         setStyle(new String[]{NO_BOLD}, sE[0], sE[1]);
                         break;
                    case SET_TEXT_STYLE_I
                         setStyle(new String[]{NO_ITALIC}, sE[0], sE[1]);
                         break;
                    case SET_TEXT_STYLE_U
                         setStyle(new String[]{NO_UNDERLINE}, sE[0], sE[1]);
                         break;
                    case SET_TEXT_STYLE_S
                         setStyle(new String[]{NO_STRIKETHROUGH}, sE[0], sE[1]);
                         break;
                    case SET_SUB
                    case SET_SUPER
                    case SET_NO_SUPER
                    case SET_NO_SUB
                         undoSubSuper();
                         break;

                    case SET_TEXT_STYLE_NO_B 

                         restyle(BOLD);
                         break;
                    case SET_TEXT_STYLE_NO_I 
                         restyle(ITALIC);
                         break;
                    case SET_TEXT_STYLE_NO_U 
                         restyle(UNDERLINE);
                         break;
                    case SET_TEXT_STYLE_NO_S 
                         restyle(STRIKETHROUGH);
                         break;

                    case SET_SIZE
                         undoFSC(op);
                    case SET_TEXT_COLOR
                         undoFSC(op);
                         break;
                    case SET_FONT
                         undoFSC(op);
                         break;
               }
          }

          @Override
          public void redo()
          {
               switch (op)
               {
                    case SET_TEXT_STYLE_B
                         setStyle(new String[]{BOLD}, sE[0], sE[1]);
                         break;
                    case SET_TEXT_STYLE_I
                         setStyle(new String[]{ITALIC}, sE[0], sE[1]);
                         break;
                    case SET_TEXT_STYLE_U
                         setStyle(new String[]{UNDERLINE}, sE[0], sE[1]);
                         break;
                    case SET_TEXT_STYLE_S
                         setStyle(new String[]{STRIKETHROUGH}, sE[0], sE[1]);
                         break;
                    case SET_TEXT_STYLE_NO_B
                         setStyle(new String[]{NO_BOLD}, sE[0], sE[1]);
                         break;
                    case SET_TEXT_STYLE_NO_I
                         setStyle(new String[]{NO_ITALIC}, sE[0], sE[1]);
                         break;
                    case SET_TEXT_STYLE_NO_U
                         setStyle(new String[]{NO_UNDERLINE}, sE[0], sE[1]);
                         break;
                    case SET_TEXT_STYLE_NO_S
                         setStyle(new String[]{NO_STRIKETHROUGH}, sE[0], sE[1]);
                         break;

                    case SET_SIZE
                         setStyle(new String[]{SIZE, fsc}, sE[0], sE[1]);
                         break;
                    case SET_TEXT_COLOR
                         setStyle(new String[]{COLOR, fsc}, sE[0], sE[1]);
                         break;
                    case SET_FONT
                         setStyle(new String[]{FONT, fsc}, sE[0], sE[1]);
                         break;
                    case SET_SUB
                    case SET_SUPER
                    case SET_NO_SUPER
                    case SET_NO_SUB
                         setStyle(new String[]{VERTICAL_ALIGN, fsc}, sE[0], sE[1]);
                         break;
               }
          }

          @Override
          public byte getType()
          {
               return UndoRedoObject.STYLE;
          }

          private int[] sE = new int[2];
          private String fsc = ;
          private final byte op;
          private String[] histBlocks = new String[10];
          private int blockIndex = 0;
      }

      private class ParaUndoRedo extends java.util.LinkedListint[] implements UndoRedoObject
      {
           String paraStyle;
           int start, end;

           public ParaUndoRedo(String style, int s, int e)
           {
                paraStyle = style;
                start = s;
                end = e;
           }

           public void addParaBlock (String style, int s)
           {
                int spc;

                if (style.equalsIgnoreCase(left))
                {
                     spc = SET_PARA_STYLE_L;
                }
                else if (style.equalsIgnoreCase(right))
                {
                     spc = SET_PARA_STYLE_R;
                }
                else if (style.equalsIgnoreCase(center))
                {
                     spc = SET_PARA_STYLE_C;
                }
                else if (style.equalsIgnoreCase(justify))
                {
                     spc = SET_PARA_STYLE_J;
                }
                else
                {
                     spc = SET_PARA_STYLE_L;
                }
                this.add(new int[]{spc, s});
           }

           @Override
           public void redo() throws BadLocationException, java.io.IOException
           {
                formatParagraph(paraStyle, start, end, false);
           }

           @Override
           public void undo() throws BadLocationException, java.io.IOException
           {
                String style;
                int i = 0;
                int[] param;

                while (i  this.size())
                {
                    param = (int[]) this.get(i);
                    switch(param[0])
                    {
                         case SET_PARA_STYLE_L
                              style = left;
                              break;
                         case SET_PARA_STYLE_R
                              style = right;
                              break;
                         case SET_PARA_STYLE_C
                              style = center;
                              break;
                         case SET_PARA_STYLE_J
                              style = justify;
                              break;
                         default
                              style = left;
                              break;
                    }
                    formatParagraph(style, param[1], param[1], false);
                    i++;
                }
           }

           @Override
           public byte getType()
           {
                return UndoRedoObject.PARA_ALIGN;
           }

          @Override
          public int getStart() {
               return start;
          }

          @Override
          public int getEnd() {
               return end;
          }
      }

      private class ParaIndentUndoRedo  implements UndoRedoObject
      {
           public ParaIndentUndoRedo (int s, int e, byte o)
           {
                start = s; end = e; op = o;
           }

           @Override
           public void undo() throws BadLocationException, java.io.IOException
           {
               if (op == 0) 
               {
                    setParagraphIndent((byte) 2, start, end, false);
               }
               else
               {
                    setParagraphIndent((byte) 0, start, end, false);
               }
           }

           @Override
           public void redo() throws BadLocationException, java.io.IOException
           {
                setParagraphIndent(op, start, end, false);
           }   

           @Override
           public byte getType()
           {
                return UndoRedoObject.PARA_INDENT;
           }

           @Override
          public int getStart() {
               return start;
          }

          @Override
          public int getEnd() {
               return end;
          }

           int start, end;
           byte op;
      }

      private class QuickFormatUR implements UndoRedoObject
      {
           DeleteSelUndoRedo delSar, felSar;
           private final int start, end;

           public QuickFormatUR(int start, int end, boolean fs, QuickFormatScheme qfs)
           {
                felSar = null;
                this.start = start; this.end = end;

                processDeleteSelection();
                delSar = bur;
                try
                {
                    if (qfs.align != null)
                    {
                         formatParagraph(qfs.align, start, end, false);
                    }
                    if (start != end) 
                    {
                         extSetStyle(new String[] {qfs.bold}, start, end);
                         extSetStyle(new String[] {qfs.italic},start, end);
                         extSetStyle(new String[] {qfs.underline}, start, end);
                         extSetStyle(new String[] {qfs.strikeThrough}, start, end);
                         if (qfs.font != null)
                         {
                              extSetStyle(new String[] {FONT, qfs.font}, start, end);
                         }
                         if (qfs.size != null)
                         {
                              extSetStyle(new String[] {SIZE, qfs.size}, start, end);
                         }
                         if (qfs.color != null)
                         {
                              extSetStyle(new String[] {COLOR, qfs.color}, start, end);
                         }

                         detach();
                         resetSelection(start, end, fs);
                    }
                }
                catch(java.io.IOException  javax.swing.text.BadLocationException ioe )
                {
                     resetUndoRedo();
                }
           }

           @Override
           public void undo() throws javax.swing.text.BadLocationException, java.io.IOException
           {
                processDeleteSelection(start, end);
                felSar = bur;
                deleteText(start, end);
                delSar.undo();
           }

           @Override
           public void redo() throws javax.swing.text.BadLocationException, java.io.IOException
           {
                deleteText(start, end);
                felSar.undo();
           }     

           @Override
           public byte getType()
           {
                return UndoRedoObject.QUICK_FORMAT;
           }

           @Override
           public int getStart() {
               return start;
          }

           @Override
           public int getEnd() {
               return end;
          }
      }

      private class QuickFormatScheme
      {
           public final String bold, italic, underline, strikeThrough;
           public final String align;
           public final String font;
           public final String color;
           public final String size;
           public final boolean b, i, u, s;

           public QuickFormatScheme
                   (boolean bold, boolean italic, boolean underline, boolean strike, String font,
                           String size, String color, String align)
           {    
                b = bold;
                i = italic;
                u = underline;
                s = strike;

                if (bold)
                {
                     this.bold = BOLD;
                }
                else
                {
                     this.bold = NO_BOLD;
                }

                if (italic)
                {
                     this.italic = ITALIC;
                }
                else
                {
                     this.italic = NO_ITALIC;
                }

                if (underline)
                {
                     this.underline = UNDERLINE;
                }
                else
                {
                     this.underline = NO_UNDERLINE;
                }

                if (strike)
                {
                     this.strikeThrough = STRIKETHROUGH;
                }
                else
                {
                     this.strikeThrough = NO_STRIKETHROUGH;
                }

                if (font == null  font.length() == 0)
                {
                     this.font = null;
                }
                else
                {
                     this.font = font;
                }

                if (color == null  color.length() == 0)
                {
                     this.color = null;
                }
                else
                {
                     this.color = color;
                }

                if (size == null  size.length() == 0)
                {
                     this.size = null;
                }
                else
                {
                     this.size = size;
                }

                if (align == null  align.length() == 0)
                {
                     this.align = null;
                }
                else
                {
                     this.align = align;
                }
           }   
      }

      private class DeletedPart 
      {
           public DeletedPart(String txt, javax.swing.text.AttributeSet s)
           {
                as = s;
                text = txt;
                isText = true;
           }

           public DeletedPart(byte s, byte lst, javax.swing.text.AttributeSet ass) 
           {
               isNewLine = s  0;
               isNewParagraph = s  0;
               isText = (s == 0);
               BRskip = lst;
               as = ass;
           }

           public byte getBRdelete ()
           {
                return BRskip;
           }

           public boolean isNewLine()
           {
                return isNewLine;
           }

           public boolean isText()
           {
                return isText;
           }

           public boolean isNewParagraph()
           {
                return isNewParagraph;
           }

           public void thisIsNewParagraph()
           {
                isNewParagraph = true;
           }

           public String getText()
           {
                return text;
           }

           public javax.swing.text.AttributeSet getAttributes()
           {
                return as;
           }

           public void setSkipThis()
           { skipThis = true;}  

           public boolean skipThis()
           { return skipThis;}

           private javax.swing.text.AttributeSet as = null;
           private String text = ;
           private boolean isNewLine = false, isNewParagraph = false, skipThis = false; 
           private final boolean isText;
           private byte BRskip = 0;
      }

      private void detach()
      {
           boolean ib = reflectStyle;
           reflectStyle = false;
           this.setDocument(xdoc); this.setDocument(doc);
           reflectStyle = ib;
      }

      private class UndoRedo
      {
          private final int size; 
          private int pivot = 0, insertLocation = 1, urPoint = 0;
          boolean undoable, redoable, orUndoable, orRedoable, cont;
          UndoRedoObject[] item;

          public UndoRedo(int s)
          {
               undoable = redoable = false;
               size = s + 1;
               item = new UndoRedoObject[size];
               cont = true;
               resetUndoRedoButtons();
          }

          public void addOperation(UndoRedoObject opr)
          {
               orUndoable = undoable; orRedoable = redoable;
               if (insertLocation == pivot)
               {
                    pivot = ++pivot % size;
               }

               item[insertLocation] = opr;

               insertLocation = ++insertLocation % size;

               undoable = true;
               redoable = false;
               resetUndoRedoButtons();
          }

          public UndoRedoObject undo()
                  throws javax.swing.text.BadLocationException, java.io.IOException
          {
               orUndoable = undoable; orRedoable = redoable;
               UndoRedoObject urObj;

               urObj = item[urPoint];

               urObj.undo();

               --urPoint;
               if (urPoint  0) { urPoint = size - 1; }

               redoable = true;

               if (urPoint == pivot)
               {

                    undoable = false;
               }
               resetUndoRedoButtons();
               return urObj;
          }

          public UndoRedoObject redo()
                  throws javax.swing.text.BadLocationException, java.io.IOException
          {
               orUndoable = undoable; orRedoable = redoable;
               UndoRedoObject urObj;

               urPoint = ++urPoint % size;
               urObj = item[urPoint];

               urObj.redo();

               if ((urPoint + 1) % size == insertLocation)
               {

                    redoable = false;
               }

               undoable = true;
               resetUndoRedoButtons();
               return urObj;
          }

          private void resetUndoRedoButtons ()
          {
               boolean f = (undoable != orUndoable)  (redoable != orRedoable);
               if (f && !cont)
               {
                    RichTextEditor.this.setComponentPopupMenu(getPopMenu());
               }
               cont = false;        
               if (buttonSet)
               {
                    try
                    {
                         toolUndo.setEnabled(undoable);
                         toolRedo.setEnabled(redoable);
                    }
                    catch (java.lang.NullPointerException npe){}
               }    
          }

          public UndoRedoObject getUndoObject()
          {
               if (undoable)
               {
                    return item[urPoint];
               }
               else
               {
                    return null;
               }
          }

          public UndoRedoObject getRedoObject()
          {
               if (redoable)
               {
                    return item[((urPoint + 1) % size)];
               }
               else
               {
                    return null;
               }
          }

          public void setUndoRedoPoint ()
          {
               urPoint = insertLocation - 1;
               if (urPoint  0)
               {
                    urPoint = size - 1; 
               }
          }

          public void resetInsertLocation ()
          {
               insertLocation = (urPoint + 1) % size;

          }

          public int getSize()
          {
               return size - 1;
          }
      }

      private class FontDialog extends javax.swing.JDialog
      {
           public FontDialog(String[] fnt)
           {
                super();
                super.setTitle(Font);
                _specT = true;
                initComponents(fnt);
                _specT = false;
                showing = false;
           }

           private void fntDlgHide ()
           {
                super.setVisible(false);
                resetStyleQ();
                b.requestFocusInWindow();
                showing = false;
           }

           private void fntDlgShow ()
           {
                requestFocusInWindow();
                setVisible(true);
                showing = true;
           }

           public javax.swing.JComboBoxString getSizeCombo()
           {
                return size;
           }

           private void initComponents(String[] fnt)
           {
                java.awt.Dimension bDim = new java.awt.Dimension(24, 28);
                java.awt.Dimension bDim2 = new java.awt.Dimension(28, 28);

                this.addWindowFocusListener(new java.awt.event.WindowFocusListener() 
                {
                     @Override
                     public void windowGainedFocus(WindowEvent e) {
                     }

                     @Override
                     public void windowLostFocus(WindowEvent e) {
                          fntDlgHide();

                     }
                });

                p1 = new javax.swing.JToolBar();
                p1.setFloatable(false);
                p1.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));

                font = new javax.swing.JComboBox();
                font.setSize(160, 28);
                size = new javax.swing.JComboBox();

                addStuffToSizeBox(size, sizes);

                color = new javax.swing.JButton(Color);
                color.addActionListener(new java.awt.event.ActionListener() {
                     @Override
                     public void actionPerformed(ActionEvent e) {
                          setColor();
                          fntDlgShow();
                     }
                });

                int count = 0;

                while (count  fnt.length)
                {
                     font.addItem(fnt[count]);
                     count++;
                }

                b = new javax.swing.JToggleButton
                    (htmlspan style=font-weightbold;Bspanhtml);
                b.setFont(new java.awt.Font(sansserif, 1, 12));
                b.setMaximumSize(bDim); b.setMinimumSize(bDim); b.setPreferredSize(bDim);
                b.setActionCommand(BOLD);
                b.addActionListener(eventHandler);

                i = new javax.swing.JToggleButton
                    (htmlspan style=font-weightbold;font-styleitalic;Ispanhtml);
                i.setFont(new java.awt.Font(sansserif, 1, 12));
                i.setMaximumSize(bDim); i.setMinimumSize(bDim); i.setPreferredSize(bDim);
                i.setActionCommand(ITALIC);
                i.addActionListener(eventHandler);

                u = new javax.swing.JToggleButton
                    (htmlspan style=font-weightbold;text-decorationunderline;Uspanhtml);
                u.setFont(new java.awt.Font(sansserif, 1, 12));
                u.setMaximumSize(bDim); u.setMinimumSize(bDim); u.setPreferredSize(bDim);
                u.setActionCommand(UNDERLINE);
                u.addActionListener(eventHandler);

                s = new javax.swing.JToggleButton
                    (htmlspan style=font-weightbold;text-decorationline-through;Sspanhtml);
                s.setFont(new java.awt.Font(sansserif, 1, 12));
                s.setMaximumSize(bDim); s.setMinimumSize(bDim); s.setPreferredSize(bDim);
                s.setActionCommand(STRIKETHROUGH);
                s.addActionListener(eventHandler);

                sub = new javax.swing.JToggleButton
                    (html&nbsp;span style=font-weightbold;font-sizesmaller;vertical-alignsub;Subspanhtml);
                sub.setFont(new java.awt.Font(sansserif, 1, 12));
                sub.setMaximumSize(bDim2); sub.setMinimumSize(bDim2); sub.setPreferredSize(bDim2);
                sub.addActionListener(new java.awt.event.ActionListener() {
                     @Override
                     public void actionPerformed(ActionEvent e) {
                          setSubscript();
                          sup.setSelected(isSuper);
                          sub.setSelected(isSub);
                     }
                });

                sup = new javax.swing.JToggleButton
                    (html&nbsp;span style=font-weightbold;font-sizesmaller;vertical-alignsuper;Subspanhtml);
                sup.setFont(new java.awt.Font(sansserif, 1, 12));
                sup.setMaximumSize(bDim2); sup.setMinimumSize(bDim2); sup.setPreferredSize(bDim2);
                sup.addActionListener(new java.awt.event.ActionListener() {
                     @Override
                     public void actionPerformed(ActionEvent e) {
                          setSuperscript();
                          sup.setSelected(isSuper);
                          sub.setSelected(isSub);
                     }
                });

                font.addActionListener(new java.awt.event.ActionListener() {
                     @Override
                     public void actionPerformed(ActionEvent e) {

                         try
                         {
                              Object f = font.getSelectedItem();
                              RichTextEditor.this.setFont(f.toString());
                         }
                         catch (java.lang.NullPointerException npe) 
                         { 
                              resetUndoRedo(); 
                         }
                     }
                });

                size.addActionListener(new java.awt.event.ActionListener() {

                     @Override
                     public void actionPerformed(ActionEvent e) {
                         try
                         {
                              Object s = size.getSelectedItem();
                              RichTextEditor.this.setSize(s.toString());
                         }
                         catch (java.lang.NullPointerException npe) 
                         { 
                              resetUndoRedo(); 
                         }
                     }
                });

                this.setUndecorated(true);

                p1.add(font);
                p1.add(size);
                p1.add(b);
                p1.add(i);
                p1.add(u);
                p1.add(s);
                p1.add(sup);
                p1.add(sub);
                p1.add(color);

                this.getContentPane().setLayout(new javax.swing.BoxLayout(this.getContentPane(), javax.swing.BoxLayout.PAGE_AXIS));
                this.getContentPane().add(p1);

                super.pack();
           }

           public boolean showing()
           {
                return showing;
           }

           public void hideDlg()
           {
                fntDlgHide();
           }   

           public void showDlg()
           {
                fntDlgShow();
           }

           public void show(boolean[] bius, String fnt, String size, int x, int y)
           {
                b.setSelected(isBold);
                i.setSelected(isItalic);
                u.setSelected(isUnderlined);
                s.setSelected(isStriked);

                sup.setSelected(isSuper);
                sub.setSelected(isSub);

                _specT = true;
                this.font.setSelectedIndex(0);
                this.font.getModel().setSelectedItem(dispFont);

                this.size.setSelectedIndex(0);
                this.size.getModel().setSelectedItem(dispSize);
                _specT = false;

                this.setLocation(txtGetX(), txtGetY());
                super.setVisible(true);

                showing = true;
           }

           @Override
           public void setVisible(boolean b) 
           {}

           private javax.swing.JToolBar p1;
           private javax.swing.JToggleButton b, i, u, s, sub, sup;
           private javax.swing.JComboBoxString font, size;
           private javax.swing.JButton color;

           boolean showing;
      }

      private class SearchRecord
      {
           public int[] foundLocation;
           public int currentIndex;
           public String searchKey;
           public boolean searchWhole;
           public boolean caseSensitive;
      }

      private class EventHandler implements 
              java.awt.event.ActionListener, 
              javax.swing.event.CaretListener,
              java.awt.event.KeyListener,
              java.awt.event.MouseListener,
              java.awt.event.MouseMotionListener,
              java.awt.event.ComponentListener,
              java.awt.event.FocusListener,
              javax.swing.event.PopupMenuListener
      {
          @Override
          public void actionPerformed(ActionEvent e) 
          {
               buttonExecAction(e);
          }

          @Override
          public void caretUpdate(CaretEvent e) 
          {
               txtCaretUpdate(e);
          }

          @Override
          public void keyTyped(KeyEvent e) 
          {
               RichTextEditor.this.txtKeyTyped(e);
          }

          @Override
          public void keyPressed(KeyEvent e) 
          {
               try
               {
                    txtKeyPressed(e);
               }
               catch (javax.swing.text.BadLocationException ble)
               {
                    resetUndoRedo();
               }
          }

          @Override
          public void keyReleased(KeyEvent e) 
          {
               txtKeyReleased();
          }

          @Override
          public void mouseClicked(MouseEvent e) 
          {
               txtMouseClicked(e);
          }

          @Override
          public void mousePressed(MouseEvent e) 
          {
               reflectStyle = true;
          }

          @Override
          public void mouseEntered(MouseEvent e) 
          {
          }

          @Override
          public void mouseExited(MouseEvent e) 
          {
          }

          @Override
          public void mouseDragged(MouseEvent e) 
          {
               txtMouseDragged(e);
          }

          @Override
          public void mouseMoved(MouseEvent e) 
          {
               setCursor(cursor);
          }

          @Override
          public void mouseReleased(MouseEvent e) 
          {
          }

          @Override
          public void componentResized(ComponentEvent e) 
          {
               txtComponentResized(e);
          }

          @Override
          public void componentMoved(ComponentEvent e) 
          {
          }

          @Override
          public void componentShown(ComponentEvent e) 
          {
          }

          @Override
          public void componentHidden(ComponentEvent e) 
          {
          }

          @Override
          public void focusGained(FocusEvent e) 
          {
               artfFocusGained(e);
          }

          @Override
          public void focusLost(FocusEvent e) 
          {
               artfFocusLost(e);
          }

          @Override
          public void popupMenuWillBecomeVisible(PopupMenuEvent e) 
          {
          }

          @Override
          public void popupMenuWillBecomeInvisible(PopupMenuEvent e) 
          {
               dialogOpened = true;
          }

          @Override
          public void popupMenuCanceled(PopupMenuEvent e) 
          {
          }
      }

      interface UndoRedoObject
      {
           byte INSERT = 1; 
           byte DELETE = 2; 
           byte DELETE_SELECTION = 3; 
           byte STYLE = 4; 
           byte PARA_ALIGN = 5; 
           byte PARA_INDENT = 6; 
           byte REPLACE = 7; 
           byte QUICK_FORMAT = 8; 

           void undo() throws javax.swing.text.BadLocationException, java.io.IOException;
           void redo() throws javax.swing.text.BadLocationException, java.io.IOException;
           int getStart();
           int getEnd();
           byte getType();
      }

      EventHandler eventHandler;

      javax.swing.text.html.HTMLDocument doc, xdoc ;
      javax.swing.JTextArea out;

      javax.swing.JToolBar tool;

      java.text.SimpleDateFormat sdf;
      java.text.SimpleDateFormat stf;

      javax.swing.JToggleButton toolB;
      javax.swing.JToggleButton toolI;
      javax.swing.JToggleButton toolU;
      javax.swing.JToggleButton toolS;
      javax.swing.JToggleButton toolSub;
      javax.swing.JToggleButton toolSuper;

      private boolean buttonSet;
      private boolean isFinalized;

      private boolean reflectStyle;

      private boolean isBold;
      private boolean isItalic;
      private boolean isUnderlined;
      private boolean isStriked;

      private boolean isSuper;
      private boolean isSub;              

      private boolean isParaLeft;
      private boolean isParaRight;
      private boolean isParaCenter;
      private boolean isParaJustify;

      javax.swing.JToggleButton toolParaL;
      javax.swing.JToggleButton toolParaR;
      javax.swing.JToggleButton toolParaC;
      javax.swing.JToggleButton toolParaJ;

      javax.swing.JButton toolC, popMenuC, toolIncIndent, toolDecIndent;

      javax.swing.JButton toolUndo;
      javax.swing.JButton toolRedo;
      javax.swing.JComboBoxString toolF;
      javax.swing.JComboBoxString toolSize;

      private final static String FONT = font-family;
      private final static String COLOR = color;
      private final static String DECORATION = text-decoration;
      private final static String WEIGHT = font-weight;
      private final static String STYLE = font-style;
      private final static String SIZE = font-size;

      public final static String BOLD = bold;

      public final static String NO_BOLD = no-bold;

      public final static String ITALIC = italic;

      public final static String NO_ITALIC = no-italic;

      public final static String UNDERLINE = underline;

      public final static String NO_UNDERLINE = no-underline;

      public final static String STRIKETHROUGH = line-through;

      public final static String NO_STRIKETHROUGH = no-strike;

      private final static String UNDER_AND_THROUGH = line-through underline;
      private final static String VERTICAL_ALIGN  = vertical-align;

      public final static String SUBSCRIPT = sub;

      public final static String SUPERSCRIPT = super;

      public final static String PARA_LEFT = left;

      public final static String PARA_RIGHT = right;

      public final static String PARA_CENTER = center;

      public final static String PARA_JUSTIFY = justify;

      private String[] fonts;
      private int[] sizes;

      private String prevFont;
      private String prevSize;
      private String prevColor;
      private String currentColor;

      private String defaultFont;
      private String defaultSize;
      private String defaultAlignment;

      private String dispFont;
      private String dispSize;

      private final static int INDENT_SIZE = 25;

      private byte prevOp;
      private Object currentOp;

      private int deleteCaretLoc;
      private int insertCaretPos;
      private int specInsertStart;

      private javax.swing.text.Caret karet;

      private byte cleanDeleteEndOfPara;
      private boolean deleteBlankPara;
      private byte arrowDir;

      private final static byte TEXT_ENTRY = 1;
      private final static byte BSPACE_DELETE = 2;
      private final static byte DELETE_DELETE = 3;
      private final static byte CARET_MOVE = 4;
      private final static byte DELETE_SELECTION = 6;

      private final static byte SET_TEXT_COLOR   = 50;
      private final static byte SET_TEXT_STYLE_B = 51;
      private final static byte SET_TEXT_STYLE_I = 52;
      private final static byte SET_TEXT_STYLE_U = 53;
      private final static byte SET_TEXT_STYLE_S = 54;
      private final static byte SET_SUB = 55;
      private final static byte SET_SUPER = 56;
      private final static byte SET_NO_SUB = 57;
      private final static byte SET_NO_SUPER = 58;
      private final static byte SET_FONT         = 59;
      private final static byte SET_TEXT_STYLE_NO_B = 60;
      private final static byte SET_TEXT_STYLE_NO_I = 61;
      private final static byte SET_TEXT_STYLE_NO_U = 62;
      private final static byte SET_TEXT_STYLE_NO_S = 63;
      private final static byte SET_SIZE = 64;

      private final static byte SET_PARA_STYLE_L = 101;
      private final static byte SET_PARA_STYLE_R = 102;
      private final static byte SET_PARA_STYLE_C = 103;
      private final static byte SET_PARA_STYLE_J = 104;
      private final static byte SET_PARA_INDENT = 105;

      private final static byte QUICK_FORMAT = 106;

      private final static byte UNDO = 111;
      private final static byte REDO = 112;
      private final static byte REPLACE = 121;

      private boolean attySetChanged;
      private javax.swing.text.AttributeSet 
              globAttySet,
              prevGlobAttySet, 
              ppNextAS, 
              ppCurrentAS, 
              ppPrevAS; 
      private StyleUndoRedo BIUSplanInstance;
      private DeleteUndoRedo deleteURInstance;
      private ParaUndoRedo paraURInstance;
      private ReplaceUndoRedo replaceUndoRedo;
      private ParaIndentUndoRedo indentUndoRedo;

      private DeleteSelUndoRedo bur;

      private String deleteString;
      private byte deleteCode;

      int styleQLoc; 

      boolean[] biusQ; 
      String[] fscQ; 
      String subSuperQ; 

      java.awt.Cursor cursor;

      SearchRecord searchRecord;

      FontDialog fntDlg;

      private int addStuff;

      private String contentRich, contentPlain;

      Search searchDlg; 
      SearchReplace searchReplaceDlg;

      private boolean pageSet;
      private boolean dialogOpened;

      private UndoRedo urMgr;   

      QuickFormatScheme[] quickFormat;

      javax.swing.JScrollPane theJSP;
      javax.swing.JFrame theFrame;
}
