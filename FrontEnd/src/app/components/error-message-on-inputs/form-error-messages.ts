export const ERROR_MESSAGES: Record<string, string> = 
{
  required: "Ce champ est requis.",
  email: "Format d'email invalide.",
  phone: "Numéro de téléphone invalide.",
  postalCode: "Code postal invalide (5 chiffres requis).",
  invoiceNumber: "Numéro de facture invalide (ex: F0000-00000).",
  shareCapital: "Format invalid. (ex: 10 000, 10000, 10 000.50).",
  siren: "9 chiffres au total, espaces compris.",
  siret: "14 chiffres au total, espaces compris. SIREN (9) + NIC (5)",
  rcs: "RCS + votre ville  + SIREN(9).",
  tva: "FR + 2(chiffres/lettres) + SIREN(9).",
  pattern: "Valeur invalide.",
  password: "Au moins huit caractères, au moins une lettre majuscule, une lettre minuscule, un chiffre et un caractère spécial : \" @  $  !  %  *  ?  & \"",
  passwordMismatch: "Les mots de passe ne correspondent pas."
};
