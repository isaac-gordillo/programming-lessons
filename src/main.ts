import {chunk} from 'lodash';
import {from} from 'rxjs';


const ids = [1, 2, 3, 4, 5, 6, 7];

const chunkedIds = chunk(ids, 2);


from(chunkedIds).subscribe(console.log)

from(ids).subscribe(console.log)