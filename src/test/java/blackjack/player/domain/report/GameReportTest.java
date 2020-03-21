package blackjack.player.domain.report;

import static blackjack.player.domain.component.PlayerInfoHelper.*;
import static org.assertj.core.api.Assertions.*;

import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;

import blackjack.card.domain.GameResult;
import blackjack.player.domain.component.Money;
import blackjack.player.domain.component.PlayerInfo;

class GameReportTest {

	private static Stream<Arguments> gameReportProvider() {
		return Stream.of(
			Arguments.of(
				new GameReport(new PlayerInfo("앨런", Money.create(1000)), GameResult.WIN), Money.create(1000)
			),
			Arguments.of(
				new GameReport(new PlayerInfo("앨런", Money.create(1000)), GameResult.DRAW), Money.create(0)
			),
			Arguments.of(
				new GameReport(new PlayerInfo("앨런", Money.create(1000)), GameResult.LOSE), Money.create(-1000)
			),
			Arguments.of(
				new GameReport(new PlayerInfo("앨런", Money.create(1000)), GameResult.BLACKJACK_WIN), Money.create(1500)
			)
		);
	}

	@DisplayName("플레이어 정보가 없으면 Exception")
	@ParameterizedTest
	@NullSource
	void test(PlayerInfo arongPlayerInfo) {
		assertThatThrownBy(() -> {
			new GameReport(arongPlayerInfo, GameResult.WIN);
		}).isInstanceOf(NullPointerException.class);
	}

	@DisplayName("승패 정보가 없을 때 게임결과를 만들면 Exception")
	@ParameterizedTest
	@NullSource
	void test2(GameResult gameResult) {
		assertThatThrownBy(() -> {
			new GameReport(aPlayerInfo("allen"), gameResult);
		}).isInstanceOf(NullPointerException.class);
	}

	@DisplayName("플레이어의 승패결과에 따른 수익계산")
	@ParameterizedTest
	@MethodSource("gameReportProvider")
	void calculateGamblerProfit(GameReport gameReport, Money expect) {
		Money actual = gameReport.calculateGamblerProfit();
		assertThat(actual).isEqualTo(expect);
	}
}