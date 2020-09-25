import { FormGroup, AbstractControl, Validators, ValidatorFn, ValidationErrors } from '@angular/forms';

export class ValidacionesComponent {

    static MustMatch(controlName: string, matchingControlName: string) {
        return (formGroup: FormGroup) => {
            const control = formGroup.controls[controlName];
            const matchingControl = formGroup.controls[matchingControlName];

            if (matchingControl.errors && !matchingControl.errors.mustMatch) {
                // return if another validator has already found an error on the matchingControl
                return;
            }
            // set error on matchingControl if validation fails
            if (control.value !== matchingControl.value) {
                matchingControl.setErrors({ mustMatch: true });
            } else {
                matchingControl.setErrors(null);
            }
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

    static minValue(min: Number): ValidatorFn {
        return (control: AbstractControl): { [key: string]: any } => {
            const input = control.value,
                isValid = input < min;
            if (isValid)
                return { 'minValue': { min } }
            else
                return null;
        };
    }
    
    static patternValidator(regex: RegExp, error: ValidationErrors): ValidatorFn {
        return (control: AbstractControl): { [key: string]: any } => {
            if (!control.value) {
                // if control is empty return no error
                return null;
            } 
            // test the value of the control against the regexp supplied
            const valid = regex.test(control.value); 
            // if true, return no error (no error), else return error passed in the second parameter
            return valid ? null : error;
        };
    }



}
