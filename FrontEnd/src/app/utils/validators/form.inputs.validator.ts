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
}
