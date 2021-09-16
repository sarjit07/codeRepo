import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;


public class LRUTest {

    @InjectMocks
    LRU lru = new LRU(3);

    @Test
    public void test_put_success() {
        LRU lruMock = Mockito.mock(LRU.class);
        Mockito.doNothing().when(lruMock).put(Mockito.any(Integer.class), Mockito.any(Integer.class));
        lruMock.put(1, 10);
        Mockito.verify(lruMock, Mockito.times(1)).put(1, 10);
    }

    @Test
    public void test_put_success_two() {
        Assertions.assertDoesNotThrow(() -> lru.put(2, 20));
    }

    @Test
    public void test_put_success_three() {
        Assertions.assertDoesNotThrow(() -> lru.put(Integer.MAX_VALUE + 1, 50));
    }

    @Test
    public void test_get_success() {
        lru.put(3, 30);
        int value = lru.get(3);
        Assertions.assertTrue(value == 30);
    }

    @Test
    public void test_get_return_minus_one() {
        int value = lru.get(10);
        Assertions.assertTrue(value == -1);
    }
}
