package beadss;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(Lotto.class)
public class LottoGameTest {

	@Test
	public void processResult() {
		PowerMockito.mockStatic(Lotto.class);
		Lotto lottoForTest1 = new Lotto(Arrays.asList(11,12,13,14,15,16));
		Lotto lottoForTest2 = new Lotto(Arrays.asList(1,12,13,14,15,16));
		Lotto lottoForTest3 = new Lotto(Arrays.asList(1,2,13,14,15,16));
		Lotto lottoForTest4 = new Lotto(Arrays.asList(1,2,3,14,15,16));
		Lotto lottoForTest5 = new Lotto(Arrays.asList(1,2,3,4,15,16));
		Lotto lottoForTest6 = new Lotto(Arrays.asList(1,2,3,4,5,16));
		Lotto lottoForTest7 = new Lotto(Arrays.asList(1,2,3,4,5,6));

		when(Lotto.generateRandomLotto())
				.thenReturn(lottoForTest1)
				.thenReturn(lottoForTest2)
				.thenReturn(lottoForTest3)
				.thenReturn(lottoForTest4)
				.thenReturn(lottoForTest5)
				.thenReturn(lottoForTest6)
				.thenReturn(lottoForTest7);

		when(Lotto.parse(anyString())).thenReturn(lottoForTest7);

		int amount = 10000;

		LottoGame game = new LottoGame(amount, Arrays.asList(lottoForTest7, lottoForTest7, lottoForTest7));

		LottoResult result = game.processResult(Lotto.parse(""), 1);

		assertTrue(result.getWinCount(Rank.Fifth) == 1);
		assertTrue(result.getWinCount(Rank.Fourth) == 1);
		assertTrue(result.getWinCount(Rank.Third) == 0);
		assertTrue(result.getWinCount(Rank.Second) == 1);
		assertTrue(result.getWinCount(Rank.First) == 4);
	}

	@Test(expected = UnsupportedOperationException.class)
	public void overManualCount() {
		Lotto lottoForTest = new Lotto(Arrays.asList(11,12,13,14,15,16));

		int amount = 10000;;

		List<Lotto> manualList = new ArrayList<>();

		for(int i = 0; i < amount/LottoGame.PRICE + 1; i++) {
			manualList.add(lottoForTest);
		}


		//expect exception
		new LottoGame(amount, manualList);
	}
}

