import axios from 'axios';

export const getLatestMangas = async () => {
    try {
        const response = await axios.get('/api-remote/manga');
        return response.data;
    } catch (error) {
        console.error("Lỗi khi lấy danh sách truyện:", error);
        return null;
    }
};

export const getPopularMangas = async () => {
    try {
        const response = await axios.get('/api-remote/manga/popular');
        return response.data;
    } catch (error) {
        console.error("Lỗi khi lấy danh sách truyện:", error);
        return null;
    }
};