package domain;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class LottoTicketTest {
    @Test
    public void getMatchedCountPass() throws Exception {
        List<Integer> list1 = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6));
        List<Integer> list2 = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 8, 9));
        LottoTicket lottoTicket = new LottoTicket(list1);
        LottoTicket winningLottoTicket = new LottoTicket(list2);

        // Arrays.asList는 내부적으로 ArrayList를 생성해서 쓴다. 따라서 Collection의 ArrayList랑 다르다.
//        LottoTicket lottoTicket = new LottoTicket(Arrays.asList(1, 2, 3, 4, 5, 6));
//        LottoTicket winningLottoTicket = new LottoTicket(Arrays.asList(1, 2, 3, 7, 8, 9));

        assertEquals(4, lottoTicket.getMatchedCount(winningLottoTicket));
    }
}
