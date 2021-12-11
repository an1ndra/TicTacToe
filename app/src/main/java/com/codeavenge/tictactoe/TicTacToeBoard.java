package com.codeavenge.tictactoe;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class TicTacToeBoard extends View {
    private final int boardColor;
    private final int XColor;
    private final int OColor;
    private final int winningLineColor;
    private final game_logic game;
    private final Paint paint=new Paint();
    private int cellSize=getWidth()/3;
    private boolean winningLine=false;

    public TicTacToeBoard(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        game=new game_logic();

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.TicTacToeBoard,0,0);

        try {
            boardColor=a.getInteger(R.styleable.TicTacToeBoard_boardColor,0);
            XColor=a.getInteger(R.styleable.TicTacToeBoard_XColor,0);
            OColor =a.getInteger(R.styleable.TicTacToeBoard_OColor,0);
            winningLineColor=a.getInteger(R.styleable.TicTacToeBoard_winningLineColor,0);

        }finally {
            a.recycle();
        }


    }
    @Override
    protected void onMeasure(int width,int height){
        super.onMeasure(width,height);

        int dimension = Math.min(getMeasuredHeight(),getMeasuredWidth());
        cellSize=dimension/3;

        setMeasuredDimension(dimension,dimension);
    }

    @Override
    protected void onDraw(Canvas canvas){
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);

        drawGameBoard(canvas);
        drawMarkers(canvas);

        if (winningLine){
            paint.setColor(winningLineColor);
            drawWinningLine(canvas);
        }
        /*
          This was only for testng purpose
          drawX(canvas,2,2);
          drawO(canvas,1,2);
         */
    }

    private void drawMarkers(Canvas canvas) {
        for (int i=0;i<3;i++){
            for (int j=0;j<3;j++){
                if (game.getGameBoard()[i][j]!=0){
                    if (game.getGameBoard()[i][j]==1){
                        drawX(canvas,i,j);
                    }else {
                        drawO(canvas,i,j);
                    }
                }

            }
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        float x=event.getX();
        float y=event.getY();

        int action=event.getAction();
        if (action==MotionEvent.ACTION_DOWN){
            int row=(int) Math.ceil(y/cellSize);
            int column=(int) Math.ceil(x/cellSize);

            if (!winningLine){
                if (game.updateGameBoard(row,column)){
                    invalidate();
                    if (game.winnerCheck()){
                        winningLine=true;
                        invalidate();
                    }

                    if (game.getPlayer()%2==0){
                        game.setPlayer(game.getPlayer()-1);
                    }else {
                        game.setPlayer(game.getPlayer()+1);
                    }
                }
            }

            //invalidate re will redraw tic tac toe board
            invalidate();

            return true;
        }
        return false;
    }

    private void drawGameBoard(Canvas canvas) {
        paint.setColor(boardColor);
        paint.setStrokeWidth(16);

        for (int i=1;i<3;i++){
            canvas.drawLine(cellSize*i,0,cellSize*i,canvas.getWidth(),paint);
        }for (int j=1;j<3;j++){
            canvas.drawLine(0,cellSize*j,canvas.getWidth(),cellSize*j,paint);
        }
    }
    private void drawX(Canvas canvas, int row,int column){
    paint.setColor(XColor);
    canvas.drawLine((float) ((column+1)*cellSize-cellSize*0.2),
            (float) (row*cellSize+cellSize*0.2),
            (float) (column*cellSize+cellSize*0.2),
            (float) ((row+1)*cellSize-cellSize*0.2),
                            paint);

    canvas.drawLine((float) (column*cellSize+cellSize*0.2),
            (float) (row*cellSize+cellSize*0.2),
            (float) ((column+1)*cellSize-cellSize*0.2),
            (float) ((row+1)*cellSize-cellSize*0.2),
                            paint);
    }

    private void drawO(Canvas canvas, int row,int column){
    paint.setColor(OColor);
    canvas.drawOval(
            (float) (column*cellSize+cellSize*0.2),
            (float) (row*cellSize+cellSize*0.2),
            (float) ((column*cellSize+cellSize)-cellSize*0.2),
            (float) ((row*cellSize+cellSize)-cellSize*0.2),
            paint);
    }

    private void HorizontalLine(Canvas canvas,int row,int column){
        canvas.drawLine(column,row*cellSize+(float)cellSize/2,
                cellSize*3,row*cellSize+(float)cellSize/2,paint);
    }

    private void VerticalLine(Canvas canvas,int row,int column){
        canvas.drawLine(column*cellSize+(float)cellSize/2,row,
                column*cellSize+(float)cellSize/2,
                cellSize*3,paint);{

        }
    }

    private void drawDiagonalLinePositive(Canvas canvas){
        canvas.drawLine(0,cellSize*3,
                cellSize*3,0,
                paint);
    }
    private void drawDiagonalLineNegative(Canvas canvas){
        canvas.drawLine(0,0,
                cellSize*3,cellSize*3,
                paint);
    }

    private void drawWinningLine(Canvas canvas){
        int row=game.getWinType()[0];
        int column=game.getWinType()[1];

        switch (game.getWinType()[2]){
            case 1:
                HorizontalLine(canvas,row,column );
                break;
            case 2:
                VerticalLine(canvas,row,column);
                break;
            case 3:
                drawDiagonalLineNegative(canvas);
                break;
            case 4:
                drawDiagonalLinePositive(canvas);
        }
    }


    public void setUpGame(Button playAgain, Button home, TextView playerDisplay,String[] names){
        game.setPlayAgainBtn(playAgain);
        game.setHomeBtn(home);
        game.setPlayerTurn(playerDisplay);
        game.setPlayerNames(names);
    }

    public void resetGame(){
            game.resetGame();
            winningLine=false;
    }
}
