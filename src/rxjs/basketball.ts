import {
	Observable,
	interval,
	filter,
	map,
	merge,
	mergeMap,
	takeUntil,
	Subject,
	tap,
} from 'rxjs';

type Scoreboard = Record<string, number>;

function runGame(scoreLimit: number) {
	const gameStreams = setupGame('Antonio', 'Isaac', 'Kobe');
	const gamerOver$ = new Subject<void>();
	merge(gameStreams)
		.pipe(
			mergeMap((scoreboard) => scoreboard),
			takeUntil(gamerOver$)
		)
		.subscribe({
			next(scoreboard) {
				console.log(scoreboard);

				const winner = Object.entries(scoreboard).find(
					([_player, score]) => score >= scoreLimit
				);

				if (winner) {
					const [player, score] = winner;
					console.log(
						`The winner is ${player} being the first player to reach ${score} points`
					);
					gamerOver$.next();
					gamerOver$.complete();
				}
			},
		});
}

runGame(3);

function setupGame(
	...playerNames: ReadonlyArray<string>
): ReadonlyArray<Observable<Scoreboard>> {
	const scoreboard = playerNames.reduce((scoreboard, playerName) => {
		scoreboard[playerName] = 0;
		return scoreboard;
	}, {} as Scoreboard);

	const streams = playerNames.map((playerName) =>
		getPlayerGameStream(scoreboard, playerName)
	);

	return streams;
}

function getPlayerGameStream(scoreboard: Scoreboard, playerName: string) {
	return interval(2000).pipe(
		tap(() => console.log(playerName, 'lanza')),
		filter(() => {
			const hasScored = generateRandomBoolean();
			hasScored ? console.log(`${playerName} ha encestado`) : console.log(`${playerName} ha fallado`);;
			return hasScored;
		}),
		map(() => {
			scoreboard[playerName]++;
			return scoreboard;
		})
	);
}

function generateRandomBoolean(): boolean {
	return Math.random() < 0.5;
}
