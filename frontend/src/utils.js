import CryptoJS from 'crypto-js'

export const generateSHA512Hash = (data) => {
  const hash = CryptoJS.SHA512(data);
  return hash.toString(CryptoJS.enc.Hex);
}