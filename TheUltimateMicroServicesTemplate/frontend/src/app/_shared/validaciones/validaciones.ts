
export class ValidacionesComponent {

    public static pattern(event: any, patron: string, max: number) {
        let inputChar = String.fromCharCode(event.keyCode);
        let value: string = event.target.value;
        var pattern = new RegExp(patron);
        if (!pattern.test(inputChar) || value.length >= max) {
            event.stopPropagation();
            event.preventDefault(); // invalid character, prevent input
        }
    }

    public static pattern2(event: any, patron: string, max: number) {
        let inputChar = String.fromCharCode(event.keyCode);
        let value: string = event.target.value;
        var pattern = new RegExp(patron, 'g');

        if (!pattern.test(inputChar) || value.length >= max) {
            event.stopPropagation();
            event.preventDefault(); // invalid character, prevent input
        }
    }


}
