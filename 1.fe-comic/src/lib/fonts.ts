import { Fira_Sans } from "next/font/google";

export const fira = Fira_Sans({
    subsets: ["latin"],
    weight: ["300", "400", "500", "600", "700", "800", "900"],
    variable: "--font-fira",
    display: 'swap', // Tốt cho performance
});
