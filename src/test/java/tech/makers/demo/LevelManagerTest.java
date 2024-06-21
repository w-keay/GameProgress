package tech.makers.demo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tech.makers.demo.EscapeRoomGame;
import tech.makers.demo.assets.Door;
import tech.makers.demo.levels.Level;
import tech.makers.demo.levels.LevelManager;
import tech.makers.demo.player.Player;
import javafx.scene.canvas.GraphicsContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class LevelManagerTest {

    @Mock
    private GraphicsContext mockGraphicsContext;

    @Mock
    private EscapeRoomGame mockGame;

    @InjectMocks
    private LevelManager levelManager;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this); // Initialize mocks

        // Mock levels array
        Level level1 = mock(Level.class);
        Level level2 = mock(Level.class);
        levelManager.setLevels(new Level[]{level1, level2});
    }

    @Test
    void testGetCurrentLevel() {
        // Test getCurrentLevel method
        assertSame(levelManager.getCurrentLevel(), levelManager.levels[0]);
    }

    @Test
    void testGetCurrentLevelNumber() {
        // Test getCurrentLevelNumber method
        assertEquals(1, levelManager.getCurrentLevelNumber());
    }

    @Test
    void testLoadNextLevel() {
        // Mock setupNextLevel method on game mock
        doNothing().when(mockGame).setupNextLevel();

        // Test loadNextLevel method
        levelManager.loadNextLevel();

        assertEquals(2, levelManager.getCurrentLevelNumber()); // Assert currentLevelIndex is incremented
    }

    @Test
    void testLoadNextLevel_EndOfLevels() {
        // Move to the last level
        levelManager.loadNextLevel();
        levelManager.loadNextLevel();

        // Mock System.out to capture printed output
        System.setOut(new java.io.PrintStream(outContent));

        levelManager.loadNextLevel();

        assertEquals("You have completed all levels!\n", outContent.toString()); // Check console output
    }

    @Test
    void testRender() {
        // Mock render method on GraphicsContext
        doNothing().when(mockGraphicsContext).clearRect(anyDouble(), anyDouble(), anyDouble(), anyDouble());

        // Call render method
        levelManager.render();

        // Verify render method was called on the current level
        verify(levelManager.getCurrentLevel(), times(1)).render(mockGraphicsContext);
    }

    @Test
    void testUpdate_LevelNotCompleted() {
        // Mock update method on current level
        Level mockLevel = levelManager.getCurrentLevel();
        when(mockLevel.isCompleted()).thenReturn(false);

        // Call update method
        levelManager.update();

        // Verify completeLevel() on game was not called
        verify(mockGame, never()).completeLevel();
    }

    @Test
    void testUpdate_LevelCompleted() {
        // Mock update method on current level
        Level mockLevel = levelManager.getCurrentLevel();
        when(mockLevel.isCompleted()).thenReturn(true);

        // Call update method
        levelManager.update();

        // Verify completeLevel() on game was called
        verify(mockGame, times(1)).completeLevel();
    }

    @Test
    void testCompleteLevel() {
        // Test completeLevel method
        levelManager.completeLevel();

        // Verify completeLevel() on game was called
        verify(mockGame, times(1)).completeLevel();
    }

    @Test
    void testSetLevels() {
        // Create new levels array
        Level newLevel1 = mock(Level.class);
        Level newLevel2 = mock(Level.class);
        Level[] newLevels = {newLevel1, newLevel2};

        // Set new levels array
        levelManager.setLevels(newLevels);

        // Verify levels were set correctly
        assertArrayEquals(newLevels, levelManager.levels);
    }

    private final java.io.ByteArrayOutputStream outContent = new java.io.ByteArrayOutputStream();
}






