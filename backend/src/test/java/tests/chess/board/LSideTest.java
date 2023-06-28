package tests.chess.board;

import chess.board.LSide;
import org.junit.Assert;
import org.junit.Test;

public class LSideTest {

    @Test
    public void flip() {
        Assert.assertEquals(LSide.BLACK.flip(), LSide.WHITE);
        Assert.assertEquals(LSide.WHITE.flip(), LSide.BLACK);
    }
}
