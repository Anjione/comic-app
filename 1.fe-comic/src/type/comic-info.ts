export type SeriesItem = {
    id: number;
    rank: number;
    chapter: string;
    title: string;
    href: string;
    img: string;
    genres: string[];
    score: number; // 0-10
};

export const SAMPLE_GENRES: any[] = [
    { id: 1, name: 'Action', url: 'https://komik25.com/genres/action/' },
    { id: 2, name: 'Action Adventure', url: 'https://komik25.com/genres/action-adventure/' },
    { id: 3, name: 'Adaptation', url: 'https://komik25.com/genres/adaptation/' },
    { id: 4, name: 'Adult', url: 'https://komik25.com/genres/adult/' },
    { id: 5, name: 'Adventure', url: 'https://komik25.com/genres/adventure/' },
    { id: 6, name: 'Adventure Drama', url: 'https://komik25.com/genres/adventure-drama/' },
    { id: 7, name: 'Comedy', url: 'https://komik25.com/genres/comedy/' },
    { id: 8, name: 'Cooking', url: 'https://komik25.com/genres/cooking/' },
    { id: 9, name: 'Crime', url: 'https://komik25.com/genres/crime/' },
    { id: 10, name: 'Demon', url: 'https://komik25.com/genres/demon/' },
    { id: 11, name: 'Demons', url: 'https://komik25.com/genres/demons/' },
    { id: 12, name: 'Drama', url: 'https://komik25.com/genres/drama/' },
    { id: 13, name: 'Ecchi', url: 'https://komik25.com/genres/ecchi/' },
    { id: 14, name: 'Fantasy', url: 'https://komik25.com/genres/fantasy/' },
    { id: 15, name: 'Game', url: 'https://komik25.com/genres/game/' },
    { id: 16, name: 'Gender Bender', url: 'https://komik25.com/genres/gender-bender/' },
    { id: 17, name: 'Gore', url: 'https://komik25.com/genres/gore/' },
    { id: 18, name: 'Harem', url: 'https://komik25.com/genres/harem/' },
    { id: 19, name: 'Hero', url: 'https://komik25.com/genres/hero/' },
    { id: 20, name: 'Historical', url: 'https://komik25.com/genres/historical/' },
    { id: 21, name: 'Horror', url: 'https://komik25.com/genres/horror/' },
    { id: 22, name: 'Isekai', url: 'https://komik25.com/genres/isekai/' },
    { id: 23, name: 'Josei', url: 'https://komik25.com/genres/josei/' },
    { id: 24, name: 'Kombay', url: 'https://komik25.com/genres/kombay/' },
    { id: 25, name: 'Long Strip', url: 'https://komik25.com/genres/long-strip/' },
    { id: 26, name: 'Magic', url: 'https://komik25.com/genres/magic/' },
    { id: 27, name: 'Manhwa', url: 'https://komik25.com/genres/manhwa/' },
    { id: 28, name: 'Martial Arts', url: 'https://komik25.com/genres/martial-arts/' },
    { id: 29, name: 'Mature', url: 'https://komik25.com/genres/mature/' },
    { id: 30, name: 'Mecha', url: 'https://komik25.com/genres/mecha/' },
    { id: 31, name: 'Medical', url: 'https://komik25.com/genres/medical/' },
    { id: 32, name: 'Military', url: 'https://komik25.com/genres/military/' },
    { id: 33, name: 'Monsters', url: 'https://komik25.com/genres/monsters/' },
    { id: 34, name: 'Music', url: 'https://komik25.com/genres/music/' },
    { id: 35, name: 'Musical', url: 'https://komik25.com/genres/musical/' },
    { id: 36, name: 'Mystery', url: 'https://komik25.com/genres/mystery/' },
    { id: 37, name: 'Police', url: 'https://komik25.com/genres/police/' },
    { id: 38, name: 'Project', url: 'https://komik25.com/genres/project/' },
    { id: 39, name: 'Psychological', url: 'https://komik25.com/genres/psychological/' },
    { id: 40, name: 'Regression', url: 'https://komik25.com/genres/regression/' },
    { id: 41, name: 'Reincarnation', url: 'https://komik25.com/genres/reincarnation/' },
    { id: 42, name: 'Reincarnation Seinen', url: 'https://komik25.com/genres/reincarnation-seinen/' },
    { id: 43, name: 'Returner', url: 'https://komik25.com/genres/returner/' },
    { id: 44, name: 'Romance', url: 'https://komik25.com/genres/romance/' },
    { id: 45, name: 'School', url: 'https://komik25.com/genres/school/' },
    { id: 46, name: 'School life', url: 'https://komik25.com/genres/school-life/' },
    { id: 47, name: 'Sci-fi', url: 'https://komik25.com/genres/sci-fi/' },
    { id: 48, name: 'Seinen', url: 'https://komik25.com/genres/seinen/' },
    { id: 49, name: 'Shotacon', url: 'https://komik25.com/genres/shotacon/' },
    { id: 50, name: 'Shoujo', url: 'https://komik25.com/genres/shoujo/' },
    { id: 51, name: 'Shoujo Ai', url: 'https://komik25.com/genres/shoujo-ai/' },
    { id: 52, name: 'Shounen', url: 'https://komik25.com/genres/shounen/' },
    { id: 53, name: 'Shounen Ai', url: 'https://komik25.com/genres/shounen-ai/' },
    { id: 54, name: 'Slice of Life', url: 'https://komik25.com/genres/slice-of-life/' },
    { id: 55, name: 'Sports', url: 'https://komik25.com/genres/sports/' },
    { id: 56, name: 'Super Power', url: 'https://komik25.com/genres/super-power/' },
    { id: 57, name: 'Supernatural', url: 'https://komik25.com/genres/supernatural/' },
    { id: 58, name: 'Supranatural', url: 'https://komik25.com/genres/supranatural/' },
    { id: 59, name: 'Survival', url: 'https://komik25.com/genres/survival/' },
    { id: 60, name: 'System', url: 'https://komik25.com/genres/system/' },
    { id: 61, name: 'Thriller', url: 'https://komik25.com/genres/thriller/' },
    { id: 62, name: 'Time Travel', url: 'https://komik25.com/genres/time-travel/' },
    { id: 63, name: 'Tragedy', url: 'https://komik25.com/genres/tragedy/' },
    { id: 64, name: 'Vampire', url: 'https://komik25.com/genres/vampire/' },
    { id: 65, name: 'Vampires', url: 'https://komik25.com/genres/vampires/' },
    { id: 66, name: 'Villainess', url: 'https://komik25.com/genres/villainess/' },
    { id: 67, name: 'Web Comic', url: 'https://komik25.com/genres/web-comic/' },
    { id: 68, name: 'Wuxia', url: 'https://komik25.com/genres/wuxia/' }
];

export const STATUS_LIST = [
    { value: "", label: "All" },
    { value: "ongoing", label: "Ongoing" },
    { value: "completed", label: "Completed" },
    { value: "hiatus", label: "Hiatus" },
];

export const TYPE_LIST = [
    { value: "", label: "All" },
    { value: "manga", label: "Manga" },
    { value: "manhwa", label: "Manhwa" },
    { value: "manhua", label: "Manhua" },
    { value: "comic", label: "Comic" },
    { value: "novel", label: "Novel" },
];

export const SORT_LIST = [
    { value: "", label: "Default" },
    { value: "title", label: "A-Z" },
    { value: "titlereverse", label: "Z-A" },
    { value: "update", label: "Update" },
    { value: "latest", label: "Added" },
    { value: "popular", label: "Popular" },
];

export const SERIES_DATA = [
    {
        letter: 'R',
        items: [
            { id: 143282, title: 'Risou no Himo Seikatsu', url: 'https://komik25.com/manga/risou-no-himo-seikatsu/' }
        ]
    },
    {
        letter: 'T',
        items: [
            { id: 138900, title: 'Tsuihousha Shokudou e Youkoso! (Welcome to Cheap Restaurant of Outcasts!)', url: 'https://komik25.com/manga/welcome-to-cheap-restaurant-of-outcasts/' }
        ]
    },
    {
        // Ký tự đặc biệt (ví dụ: bắt đầu bằng dấu ngoặc kép)
        letter: '"',
        items: [
            { id: 167364, title: '“Honyaku” no Sainou de Ore Dake ga Sekai wo Kaihen Dekiru Ken', url: 'https://komik25.com/manga/honyaku-no-sainou-de-ore-dake-ga-sekai-wo-kaihen-dekiru-ken/' },
            { id: 169286, title: '“Jako ni wa Kaji ga Oniaida www” to Iwareta Kaji Level 9999 no Ore, Tsuihousareta no de Boukensha ni Tensoku suru – Saikyou de Musou Shinagara Guild de Tanoshiku Kurashimasu', url: 'https://komik25.com/manga/jako-ni-wa-kaji-ga-oniaida-www-to-iwareta-kaji-level-9999-no-ore-tsuihousareta-no-de-boukensha-ni-tensoku-suru-saikyou-de-musou-shinagara-guild-de-tanoshiku-kurashimasu/' },
            { id: 134364, title: '“Shop” Skill Sae Areba, Dungeon-ka Shita Sekai Demo Rakushou da', url: 'https://komik25.com/manga/shop-skill-sae-areba-dungeon-ka-shita-sekai-demo-rakushou-da/' }
        ]
    },
    // ... Thêm các nhóm A, B, C, D, E... vào đây
];