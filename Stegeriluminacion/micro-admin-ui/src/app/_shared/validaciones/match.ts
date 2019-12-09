import { AbstractControl, Validators } from '@angular/forms';

export class PasswordValidation {

    static MatchPassword(AC: AbstractControl) {
        let password = AC.get('password').value;
        let confirmPassword = AC.get('passwordConfirm').value;
        if ((password == null || password == undefined || password == "") && (confirmPassword == null || confirmPassword == undefined || confirmPassword == "")) {
            return null;
        } else if (confirmPassword != password) {
            AC.get('passwordConfirm').setErrors({ MatchPassword: true })
        } else {
            return null;
        }
    }

    static MatchPasswordUpdate(AC: AbstractControl) {
        let password = AC.get('password').value;
        let confirmPassword = AC.get('passwordConfirm').value;
        if ((password == null || password == undefined || password == "") && (confirmPassword == null || confirmPassword == undefined || confirmPassword == "")) {
            AC.get('password').clearValidators();
            AC.get('passwordConfirm').clearValidators();
            return null;
        } else if (confirmPassword != password) {
            AC.get('password').setValidators([Validators.required, Validators.minLength(6)]);
            AC.get('passwordConfirm').setValidators([Validators.required, Validators.minLength(6)]);

            AC.get('passwordConfirm').setErrors({ MatchPassword: true })
            AC.get('password').setErrors({ MatchPassword: true })
        } else {
            return null;
        }
    }


}