import { Company } from "@app/services/company/company.models";

/*
  Functions below modify entries from direct object reference in memory passed as argument.
*/

export function formatCompanyRequest(company: Company ) :void
{
    //security convert input string safely to number
    company.shareCapital = Number(
      String(company.shareCapital).replace(/\s+/g, '').replace(',', '.')
    );

    //remove extra space on server request.
    company.siren = company.siren.replace(/\s+/g, '');
    company.siret = company.siret.replace(/\s+/g, '');
}

export function formatCompanyResponse(company: Company) : void
{
  //rollback extra space on server resp
  company.siren = company.siren.replace(/(\d{3})(?=\d)/g, '$1 ').trim();
  company.siret = company.siret.replace(/(\d{3})(?=\d)/g, '$1 ').trim();
}