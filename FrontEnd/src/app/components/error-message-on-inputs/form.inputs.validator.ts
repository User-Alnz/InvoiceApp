import { AbstractControl, ValidationErrors, ValidatorFn } from '@angular/forms';


export class CustomedValidators 
{

    static phone(): ValidatorFn 
    {
        const regex = /^(\+33|0)[1-9](\d{2}){4}$/;

        return (control: AbstractControl): ValidationErrors | null => 
        {
            if (!control.value) 
            return null;

            if(regex.test(control.value) === true)
            return null;
            else
            return { phone: true };
        };
    }

    static email(): ValidatorFn 
    {
        const regex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;

        return (control: AbstractControl): ValidationErrors | null => 
        {
            if (!control.value) 
            return null;

            if(regex.test(control.value) === true)
            return null;
            else
            return { email: true };
        };
    }

    static postalCode() : ValidatorFn
    {
        const regex = /^\d{5}$/;

        return (control: AbstractControl): ValidationErrors | null => 
        {
            if (!control.value) 
            return null;

            if(regex.test(control.value) === true)
            return null;
            else
            return { email: true };
        };
    }

    static invoicePattern() : ValidatorFn
    {
        const regex = /^F\d{4,}-\d+$/;

        return (control: AbstractControl): ValidationErrors | null => 
        {
            if (!control.value) 
            return null;

            if(regex.test(control.value) === true)
            return null;
            else
            return { invoiceNumber: true };
        };
    }

    static password() : ValidatorFn
    {
        const regex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,128}$/;
        
        return (control: AbstractControl): ValidationErrors | null =>
        {
            if (!control.value) 
            return null;

            if(regex.test(control.value) === true)
            return null;
            else
            return { password: true };
        };
    }

    //This works at FormGroup level on controls. // refer => signup-form component.
    static passwordMismatch( passwordKey : string, confirmPasswordKey : string) : ValidatorFn
    {
        return (control: AbstractControl): ValidationErrors | null =>
        {
            const password = control.get(passwordKey);
            const confirmPassword = control.get(confirmPasswordKey);

            if(!password || !confirmPassword) // simply skip validation while field are null.
                return null; 

            if(!confirmPassword.value) // prevent UX issue while not value in confirmPassword field.
                return null;

            return password.value !== confirmPassword.value ? 
                {passwordMismatch: true} : null;
        }
    }
}
