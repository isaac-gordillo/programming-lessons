let num1 = 3;
let num2 = 2;
let num3 = 30;

const numerommayour = [num1, num2, num3].sort((a, b) => a - b).shift();
console.log('weee', Math.max(num1, num2, num3))
console.log('numero pi es',Math.PI);
console.log(numerommayour);
if (num1 > num2) {
    if (num1 > num3) {
        console.log(`el numero mayor es ${num1}`);
    } else {
        console.log(`el numero mayor es ${num3}`);
    }
} else {
    if (num2 > num3) {
        console.log(`el numero mayor es ${num2}`);
    } else {
        console.log(`el numero mayor es ${num3}`);
    }
}