import {
  Subject,
  combineLatest,
  concat,
  concatMap,
  first,
  forkJoin,
  from,
  interval,
  map,
  merge,
  mergeMap,
  of,
  switchMap,
  take,
} from 'rxjs';

async function main() {
  const relojUnSegudo$ = interval(1000).pipe(take(10));
  const relojMedioSegundo$ = interval(500).pipe(take(10));

  const myPromise = (value: unknown, fail: boolean = false) => {
    return new Promise((resolve, reject) => {
      if (fail) {
        return reject('something went wront');
      }
      return resolve(value);
    });
  };

  const time = new Date();
  time.setHours(0, 0, 0, 0);

  // forkjoin will combine the provided Observables and wait until they are finished and then it will emit the results all together
  forkJoin([relojUnSegudo$, relojMedioSegundo$]).subscribe({
    next([relojUnSegudoRes, relojMedioSegundoRes]) {
      console.log('los dos resultados en la misma emisión', {
        relojUnSegudoRes,
        relojMedioSegundoRes,
      });
    },
  });

  // merge will combine all provided observables and the results will be emmited async not following a specific order, the first one to finish will be output first.
  merge(relojUnSegudo$, relojMedioSegundo$).subscribe(console.log);

  // concat will combine all provided observables and the results will be emmited sync following the order of the provided arguments
  concat(relojUnSegudo$, relojMedioSegundo$).subscribe(console.log);

  // from can convert a Promise into an Observable
  from(myPromise('hola', true)).subscribe({
    next: console.log,
    error: console.error,
  });

  // from can also conver an array of values to an Observable emmiting one element at a time (it's very useful if you need to handle many Promises in a single sream)

  const photoIds = Array.from({ length: 1000 }).map((_, index) => ++index);
  // console.log(userIds);

  const listaUsuarios$ = from(photoIds).pipe(
    concatMap(async (photoId) =>
      (
        await fetch(`https://jsonplaceholder.typicode.com/photos/${photoId}`)
      ).json()
    )
  );

  let totalTime = 0;
  const timer = setInterval(() => {
    totalTime++;
  }, 1000);

  listaUsuarios$.pipe<{ id: number; url: string }>(
    map((photo) => ({ id: photo.id, url: photo.url }))
  );

  combineLatest([relojMedioSegundo$, relojUnSegudo$]).subscribe(console.log);

  from(getPostById(1)).pipe(
    switchMap((post) => getUserById(post.userId)),
    map((user) => ({ id: user.id, username: user.username.toUpperCase() }))
  );
}

async function getPostById(
  id: number
): Promise<{ id: number; userId: number }> {
  return (
    await fetch(`https://jsonplaceholder.typicode.com/posts/${id}`)
  ).json();
}

async function getUserById(
  id: number
): Promise<{ id: number; username: string }> {
  return (
    await fetch(`https://jsonplaceholder.typicode.com/users/${id}`)
  ).json();
}

main();
