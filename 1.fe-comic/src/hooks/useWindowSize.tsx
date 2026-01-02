import { useState, useEffect } from 'react';

export const useWindowSize = () => {
    const [width, setWidth] = useState<number>(0);

    useEffect(() => {
        // Cập nhật width khi mount
        setWidth(window.innerWidth);

        const handleResize = () => setWidth(window.innerWidth);
        window.addEventListener('resize', handleResize);
        return () => window.removeEventListener('resize', handleResize);
    }, []);

    return width;
};