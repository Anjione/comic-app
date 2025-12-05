/** @type {import('tailwindcss').Config} */
module.exports = {
    content: [
      "./app/**/*.{ts,tsx}",
      "./components/**/*.{ts,tsx}",
    ],
    theme: {
      screens: {
        xs: "540px",
        sm: "640px",
        md: "768px",
        lg: "1024px",
        xl: "1280px",
        "2xl": "1536px",
      },
      container: {
        center: "true",
        padding: {
          DEFAULT: "12px",
          sm: "1rem",
          lg: "45px",
          xl: "5rem",
          "2xl": "13rem",
        },
      },
      
      extend: {
        colors: {
          web: {
            title: "#ff9601",
            titleLighter: "#ffab34",
            titleDisabled: "#808080",
          },
          dark: "#3c4858",
          black: "#161c2d",
          "dark-footer": "#192132",
          background: "hsl(var(--background))",
          foreground: "hsl(var(--foreground))",
          card: {
            DEFAULT: "hsl(var(--card))",
            foreground: "hsl(var(--card-foreground))",
          },
          popover: {
            DEFAULT: "hsl(var(--popover))",
            foreground: "hsl(var(--popover-foreground))",
          },
          primary: {
            DEFAULT: "#ff9601",
            foreground: "hsl(var(--primary-foreground))",
          },
          secondary: {
            DEFAULT: "hsl(var(--secondary))",
            foreground: "hsl(var(--secondary-foreground))",
          },
          muted: {
            DEFAULT: "hsl(var(--muted))",
            foreground: "hsl(var(--muted-foreground))",
          },
          accent: {
            DEFAULT: "hsl(var(--accent))",
            foreground: "hsl(var(--accent-foreground))",
          },
          destructive: {
            DEFAULT: "hsl(var(--destructive))",
            foreground: "hsl(var(--destructive-foreground))",
          },
          border: "hsl(var(--border))",
          input: "hsl(var(--input))",
          ring: "hsl(var(--ring))",
          chart: {
            1: "hsl(var(--chart-1))",
            2: "hsl(var(--chart-2))",
            3: "hsl(var(--chart-3))",
            4: "hsl(var(--chart-4))",
            5: "hsl(var(--chart-5))",
          },
        },
        backgroundImage: {
          "gradient-radial": "radial-gradient(var(--tw-gradient-stops))",
          "gradient-conic":
            "conic-gradient(from 180deg at 50% 50%, var(--tw-gradient-stops))",
        },
        container: {
          center: "true",
          padding: {
            DEFAULT: "12px",
            sm: "1rem",
            lg: "45px",
            xl: "5rem",
            "2xl": "13rem",
          },
        },
        zIndex: {
          1: "1",
          2: "2",
          3: "3",
          999: "999",
        },
        borderRadius: {
          lg: "var(--radius)",
          md: "calc(var(--radius) - 2px)",
          sm: "calc(var(--radius) - 4px)",
        },
        // thêm token màu/font nếu cần
      },
    },
    plugins: [
      require("@tailwindcss/forms")({
        strategy: "class", // only generate classes
      }),
      plugin(({ addBase, theme }) => {
        addBase({
          ".scrollbar": {
            overflowY: "auto",
            scrollbarColor: `${theme("colors.indigo.600")} ${theme("colors.indigo.200")}`,
            scrollbarWidth: "thin",
          },
          ".scrollbar::-webkit-scrollbar": {
            height: "4px",
            width: "4px",
            borderRadius: "99px",
          },
          ".scrollbar::-webkit-scrollbar-thumb": {
            backgroundColor: theme("colors.indigo.600"),
          },
          ".scrollbar::-webkit-scrollbar-track-piece": {
            backgroundColor: theme("colors.indigo.200"),
          },
        });
      }),
      require("tailwindcss-animate"),
    ],
  }
  