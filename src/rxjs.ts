import {
    Observable,
    Subject,
    combineLatest,
    concat,
    concatMap,
    filter,
    first,
    forkJoin,
    from,
    interval,
    map,
    merge,
    mergeMap,
    of,
    skip,
    startWith,
    switchMap,
    take,
} from 'rxjs';

async function main() {
    //   const relojUnSegudo$ = interval(1000).pipe(take(10));
    //   const relojMedioSegundo$ = interval(500).pipe(take(10));

    //   const myPromise = (value: unknown, fail: boolean = false) => {
    //     return new Promise((resolve, reject) => {
    //       if (fail) {
    //         return reject('something went wront');
    //       }
    //       return resolve(value);
    //     });
    //   };

    //   const time = new Date();
    //   time.setHours(0, 0, 0, 0);

    //   // forkjoin will combine the provided Observables and wait until they are finished and then it will emit the results all together
    //   forkJoin([relojUnSegudo$, relojMedioSegundo$]).subscribe({
    //     next([relojUnSegudoRes, relojMedioSegundoRes]) {
    //       console.log('los dos resultados en la misma emisión', {
    //         relojUnSegudoRes,
    //         relojMedioSegundoRes,
    //       });
    //     },
    //   });

    //   // merge will combine all provided observables and the results will be emmited async not following a specific order, the first one to finish will be output first.
    //   merge(relojUnSegudo$, relojMedioSegundo$).subscribe(console.log);

    //   // concat will combine all provided observables and the results will be emmited sync following the order of the provided arguments
    //   concat(relojUnSegudo$, relojMedioSegundo$).subscribe(console.log);

    //   // from can convert a Promise into an Observable
    //   from(myPromise('hola', true)).subscribe({
    //     next: console.log,
    //     error: console.error,
    //   });

    //   // from can also conver an array of values to an Observable emmiting one element at a time (it's very useful if you need to handle many Promises in a single sream)

    //   const photoIds = Array.from({ length: 1000 }).map((_, index) => ++index);
    //   // console.log(userIds);

    //   const listaUsuarios$ = from(photoIds).pipe(
    //     concatMap(async (photoId) =>
    //       (
    //         await fetch(`https://jsonplaceholder.typicode.com/photos/${photoId}`)
    //       ).json()
    //     )
    //   );

    //   let totalTime = 0;
    //   const timer = setInterval(() => {
    //     totalTime++;
    //   }, 1000);

    //   listaUsuarios$.pipe<{ id: number; url: string }>(
    //     map((photo) => ({ id: photo.id, url: photo.url }))
    //   );

    //   from(getPostById(1)).pipe(
    //     switchMap((post) => getUserById(post.userId)),
    //     map((user) => ({ id: user.id, username: user.username.toUpperCase() }))
    //   );

    const marcador = {
        jugador1: 0,
        jugador2: 0,
        jugador3: 0,
    };

    const game = setupGame('Antonio', 'Isaac', 'Kobe Bryant')
}

type Scoreboard = Record<string, number>;

function setupGame(...playerNames: ReadonlyArray<string>): ReadonlyArray<Observable<Scoreboard>> {
    const scoreboard = playerNames.reduce(
        (scoreboard, playerName) => {
            scoreboard[playerName] = 0
            return scoreboard;
        },
        {} as Scoreboard);

    const streams = playerNames.map(playerName => getGameStream(scoreboard, playerName));

    return streams;

}

function getGameStream(scoreboard: Scoreboard, playerName: string) {
    return interval(2000).pipe(
        filter(generateRandomBoolean),
        map(() => {
            console.log(`${playerName} ha encestado`);
            scoreboard[playerName]++;
            return scoreboard;
        })
    );
}
// operators that resolves Observables and Promises => mergeMap, concatMap, switchMap, exhaustMap, catchError, finalize, tap
// operator that combine Observables and promises = from, forkJoin, concat, merge, combineLatest, etc.

async function getPostById(
    id: number
): Promise<{id: number; userId: number}> {
    return (
        await fetch(`https://jsonplaceholder.typicode.com/posts/${id}`)
    ).json();
}

async function getUserById(
    id: number
): Promise<{id: number; username: string}> {
    return (
        await fetch(`https://jsonplaceholder.typicode.com/users/${id}`)
    ).json();
}

function generateRandomBoolean(): boolean {
    return Math.random() < 0.5;
}

main();
